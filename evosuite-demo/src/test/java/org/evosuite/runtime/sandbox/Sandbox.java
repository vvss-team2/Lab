package org.evosuite.runtime.sandbox;

import java.util.HashSet;
import java.util.Set;

/**
 * Java 25-compatible shadow of EvoSuite's Sandbox.
 *
 * Placed in src/test/java so it is loaded from target/test-classes (which
 * precedes the EvoSuite JAR on the test classpath) by the system classloader.
 *
 * The original Sandbox.initializeSecurityManagerForSUT() calls
 * System.setSecurityManager(), which throws UnsupportedOperationException on
 * Java 21+ because the SecurityManager API was removed.
 *
 * This shadow replaces all SecurityManager-related methods with no-ops, while
 * keeping the SandboxMode enum intact so scaffolding code that reads/writes
 * RuntimeSettings.sandboxMode continues to compile and run without errors.
 */
public class Sandbox {

    public enum SandboxMode {
        OFF, RECOMMENDED, IO
    }

    public static void setCheckForInitialization(boolean b) {
        // no-op
    }

    /** Java 25 fix: no-op — System.setSecurityManager() removed in Java 21+. */
    public static synchronized void initializeSecurityManagerForSUT() {
        // no-op
    }

    /** Java 25 fix: no-op — System.setSecurityManager() removed in Java 21+. */
    public static synchronized void initializeSecurityManagerForSUT(Set<Thread> threads) {
        // no-op
    }

    public static void addPrivilegedThread(Thread t) {
        // no-op
    }

    public static synchronized Set<Thread> resetDefaultSecurityManager() {
        return new HashSet<>();
    }

    public static boolean isSecurityManagerInitialized() {
        return false;
    }

    public static void goingToExecuteSUTCode() {
        // no-op
    }

    public static void doneWithExecutingSUTCode() {
        // no-op
    }

    public static boolean isOnAndExecutingSUTCode() {
        return false;
    }

    public static void goingToExecuteUnsafeCodeOnSameThread() {
        // no-op
    }

    public static void doneWithExecutingUnsafeCodeOnSameThread() {
        // no-op
    }

    public static boolean isSafeToExecuteSUTCode() {
        return true;
    }
}
