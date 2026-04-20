package org.evosuite.runtime.instrumentation;

/**
 * Java 25-compatible shadow of EvoSuite's RuntimeInstrumentation.
 *
 * Placed in src/test/java so it compiles into target/test-classes, which
 * appears first on the classpath.  Because EvoClassLoader (from the JAR) is
 * loaded by the system classloader, its static reference to
 * RuntimeInstrumentation.checkIfCanInstrument() is resolved via the system
 * classloader — which finds this shadow before the JAR copy.
 *
 * Making checkIfCanInstrument() always return false causes EvoClassLoader to
 * delegate every class-load to its parent (the system classloader) instead of
 * running its ASM-based bytecode transformer.  On Java 25 that transformer
 * produces incorrect max_stack values, causing VerifyError at runtime.
 */
public class RuntimeInstrumentation {

    private volatile boolean retransformingMode = false;
    private static boolean avoidInstrumentingShadedClasses = false;

    public void setRetransformingMode(boolean mode) {
        this.retransformingMode = mode;
    }

    public static void setAvoidInstrumentingShadedClasses(boolean b) {
        avoidInstrumentingShadedClasses = b;
    }

    public static boolean getAvoidInstrumentingShadedClasses() {
        return avoidInstrumentingShadedClasses;
    }

    /**
     * Java 25 fix: always return false so EvoClassLoader never tries to
     * instrument (and corrupt) class bytecode.
     */
    public static boolean checkIfCanInstrument(String className) {
        return false;
    }
}
