package com.banana;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class App {

    /**
     * Counts the number of even natural numbers in an array.
     */
    public static int countEvenNumbers(int n, int[] valuesArray) {
        if (n < 0) {
            throw new IllegalArgumentException("Error: n must be a natural number (>= 0).");
        }

        if (valuesArray == null || valuesArray.length != n) {
            throw new IllegalArgumentException("Error: Array length does not match n.");
        }

        int count = 0;
        for (int num : valuesArray) {
            if (num < 0) {
                throw new IllegalArgumentException("Error: Array contains non-natural number: " + num);
            }
            if (num % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        File inputFile = new File("in.txt");
        
        try (Scanner scanner = new Scanner(inputFile)) {
            // Read n (the first number in the file)
            if (!scanner.hasNextInt()) {
                System.out.println("Error: File is empty or does not start with an integer.");
                return;
            }
            int n = scanner.nextInt();

            // Read the n numbers into the array
            int[] arr = new int[n];
            for (int i = 0; i < n; i++) {
                if (scanner.hasNextInt()) {
                    arr[i] = scanner.nextInt();
                } else {
                    // Handle cases where n is larger than the actual count of numbers in the file
                    throw new IllegalArgumentException("Error: Expected " + n + " elements, but file ended early.");
                }
            }

            int result = countEvenNumbers(n, arr);
            
            // Output format as requested
            System.out.println("Number of even numbers in the vector=" + result);

        } catch (FileNotFoundException e) {
            System.out.println("Error: The file 'in.txt' was not found in the project root.");
        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println("An unexpected error occurred: " + e.getMessage());
        }
    }
}