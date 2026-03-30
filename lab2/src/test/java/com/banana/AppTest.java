package com.banana;

import static org.junit.Assert.*;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.PrintStream;

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
        int[] arr = {1, 2, 3, 4, 5};
        assertEquals("Should find 2 even numbers (2, 4)", 2, App.countEvenNumbers(n, arr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeNumberInArray() {
        int n = 4;
        int[] arr = {0, 2, -1, 4};
        App.countEvenNumbers(n, arr);
    }

    @Test
    public void testEmptyArray() {
        int n = 0;
        int[] arr = {};
        assertEquals("Even count for empty array should be 0", 0, App.countEvenNumbers(n, arr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMismatchedSizeEmpty() {
        int n = 2;
        int[] arr = {};
        App.countEvenNumbers(n, arr);
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMismatchedSizeOver() {
        int n = 3;
        int[] arr = {1, 2, 3, 4};
        App.countEvenNumbers(n, arr);
    }

    // --- Boundary Value Analysis (BVA) Test Cases ---

    @Test(expected = IllegalArgumentException.class)
    public void testNegativeArraySize() {
        int n = -2;
        int[] arr = {1, 2};
        App.countEvenNumbers(n, arr);
    }

    @Test
    public void testDuplicateEvens() {
        int n = 5;
        int[] arr = {5, 2, 5, 1, 2};
        assertEquals("Should count both occurrences of 2", 2, App.countEvenNumbers(n, arr));
    }

    @Test
    public void testSingleOdd() {
        int n = 1;
        int[] arr = {1};
        assertEquals("1 is not even", 0, App.countEvenNumbers(n, arr));
    }

    @Test(expected = IllegalArgumentException.class)
    public void testInvalidLargeValue() {
        int n = 1;
        int[] arr = {-2147483648};
        App.countEvenNumbers(n, arr);
    }

    // --- App.main I/O Tests ---

    @Test
    public void testMainOutputsCorrectMessage() throws Exception {
        try (FileWriter writer = new FileWriter(inputFile)) {
            writer.write("4 1 2 3 4\n");
        }

        App.main(new String[] {});

        assertTrue(outContent.toString().contains("Number of even numbers in the vector=2"));
    }

    @Test
    public void testMainHandlesMissingFile() {
        // Ensure file does not exist
        if (inputFile.exists()) {
            inputFile.delete();
        }

        App.main(new String[] {});

        assertTrue(outContent.toString().contains("Error: The file 'in.txt' was not found in the project root."));
    }
}
