package com.banana;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AppTest {

    private final PrintStream originalOut = System.out;
    private final ByteArrayOutputStream outContent = new ByteArrayOutputStream();
    private final File inputFile = new File("in.txt");

    @Before
    public void setUpStreams() {
        System.setOut(new PrintStream(outContent));
    }

    @After
    public void restoreStreamsAndCleanup() {
        System.setOut(originalOut);
        if (inputFile.exists()) {
            inputFile.delete();
        }
    }

    // --- Equivalence Class (EC) Test Cases ---

    @Test
    public void testValidInput() {
        int n = 5;
        int[] arr = { 1, 2, 3, 4, 5 };
        int result = countEvenNumbers(n, arr);
        assertEvenCount("Should find 2 even numbers (2, 4)", 2, result);
    }

    @Test
    public void testNegativeNumberInArray() {
        int n = 4;
        int[] arr = { 0, 2, -1, 4 };
        assertInvalidInput(n, arr);
    }

    @Test
    public void testEmptyArray() {
        int n = 0;
        int[] arr = {};
        int result = countEvenNumbers(n, arr);
        assertEvenCount("Even count for empty array should be 0", 0, result);
    }

    @Test
    public void testMismatchedSizeEmpty() {
        int n = 2;
        int[] arr = {};
        assertInvalidInput(n, arr);
    }

    @Test
    public void testMismatchedSizeOver() {
        int n = 3;
        int[] arr = { 1, 2, 3, 4 };
        assertInvalidInput(n, arr);
    }

    // --- Boundary Value Analysis (BVA) Test Cases ---

    @Test
    public void testNegativeArraySize() {
        int n = -2;
        int[] arr = { 1, 2 };
        assertInvalidInput(n, arr);
    }

    @Test
    public void testDuplicateEvens() {
        int n = 5;
        int[] arr = { 5, 2, 5, 1, 2 };
        int result = countEvenNumbers(n, arr);
        assertEvenCount("Should count both occurrences of 2", 2, result);
    }

    @Test
    public void testSingleOdd() {
        int n = 1;
        int[] arr = { 1 };
        int result = countEvenNumbers(n, arr);
        assertEvenCount("1 is not even", 0, result);
    }

    @Test
    public void testInvalidLargeValue() {
        int n = 1;
        int[] arr = { -2147483648 };
        assertInvalidInput(n, arr);
    }

    // --- App.main I/O Tests ---

    @Test
    public void testMainOutputsCorrectMessage() throws Exception {
        writeInputFile("4 1 2 3 4\n");
        runMain();
        assertOutputContains("Number of even numbers in the vector=2");
    }

    @Test
    public void testMainHandlesMissingFile() {
        if (inputFile.exists()) {
            inputFile.delete();
        }

        runMain();
        assertOutputContains("Error: The file 'in.txt' was not found in the project root.");
    }

    @Step("Count even numbers for n={n} and values={values}")
    private int countEvenNumbers(int n, int[] values) {
        return App.countEvenNumbers(n, values);
    }

    @Step("Verify count matches expected={expected}: {message}")
    private void assertEvenCount(String message, int expected, int actual) {
        assertEquals(message, expected, actual);
    }

    @Step("Verify invalid input throws IllegalArgumentException for n={n} and values={values}")
    private void assertInvalidInput(int n, int[] values) {
        assertThrows(IllegalArgumentException.class, () -> App.countEvenNumbers(n, values));
    }

    @Step("Write input file with contents: {contents}")
    private void writeInputFile(String contents) throws Exception {
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write(contents);
        }
    }

    @Step("Run App.main")
    private void runMain() {
        App.main(new String[] {});
    }

    @Step("Verify console output contains: {expectedText}")
    private void assertOutputContains(String expectedText) {
        assertTrue(outContent.toString().contains(expectedText));
    }
}
