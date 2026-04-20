package org.evosuite.runtime.agent;

import java.lang.instrument.Instrumentation;

/**
 * Java 25-compatible shadow of EvoSuite's InstrumentingAgent.
 *
 * Placed in src/test/java so it is compiled into target/test-classes, which
 * appears earlier on the classpath than evosuite-standalone-runtime.jar.
 * This makes the JVM load this class instead of the original.
 *
 * Fixes two Java 21+ incompatibilities in EvoSuite 1.2.0:
 *   1. AgentLoader.loadAgent() uses the Attach API to dynamically load a
 *      javaagent. The agent's TransformerForTests then rewrites class bytecode,
 *      but fails to update max_stack, causing VerifyError on Java 25's stricter
 *      verifier.  Fixed: initialize() is a no-op (agent never loaded).
 *   2. Sandbox.initializeSecurityManagerForSUT() calls System.setSecurityManager()
 *      which throws UnsupportedOperationException on Java 21+.
 *      Fixed: static initializer forces sandboxMode=OFF before @BeforeClass runs.
 */
public class InstrumentingAgent {

    private static TransformerForTests transformer;
    private static Instrumentation instrumentation;

    static {
        try {
            transformer = new TransformerForTests();
        } catch (Exception e) {
            transformer = null;
        }
        // Force sandbox mode to OFF so that Sandbox.initializeSecurityManagerForSUT()
        // becomes a no-op.  This runs before any @BeforeClass method because
        // EvoRunner loads InstrumentingAgent during its own construction (before
        // JUnit even starts running test methods).
        try {
            org.evosuite.runtime.RuntimeSettings.sandboxMode =
                    org.evosuite.runtime.sandbox.Sandbox.SandboxMode.OFF;
        } catch (Exception ignored) {
        }
    }

    /** Used when the JAR is passed as a -javaagent on the command line. */
    public static void premain(String args, Instrumentation inst) throws Exception {
        if (transformer == null) return;
        inst.addTransformer(transformer, true);
        instrumentation = inst;
    }

    /** Used when the JAR is attached dynamically via the Attach API. */
    public static void agentmain(String args, Instrumentation inst) throws Exception {
        if (transformer == null) return;
        inst.addTransformer(transformer, true);
        instrumentation = inst;
    }

    /**
     * Java 25-safe: does NOT call AgentLoader.loadAgent().
     * Dynamic agent attachment corrupts bytecode max_stack values on Java 25.
     */
    public static void initialize() {
        org.evosuite.runtime.mock.MockFramework.disable();
    }

    public static TransformerForTests getTransformer() throws IllegalStateException {
        if (transformer == null) {
            throw new IllegalStateException("EvoSuite InstrumentingAgent not properly initialized");
        }
        return transformer;
    }

    public static Instrumentation getInstrumentation() {
        return instrumentation;
    }

    /**
     * Java 25-safe: enables MockFramework and LoopCounter WITHOUT activating
     * the bytecode transformer, which would produce incorrect max_stack values
     * rejected by Java 25's verifier.
     */
    public static void activate() {
        org.evosuite.runtime.mock.MockFramework.enable();
        org.evosuite.runtime.LoopCounter.getInstance().setActive(true);
    }

    public static void deactivate() {
        org.evosuite.runtime.mock.MockFramework.disable();
        if (transformer != null) {
            try {
                transformer.deactivate();
            } catch (Exception ignored) {
            }
        }
        org.evosuite.runtime.LoopCounter.getInstance().setActive(false);
    }

    public static void setRetransformingMode(boolean mode) {
        if (transformer != null) {
            transformer.setRetransformingMode(mode);
        }
    }
}
