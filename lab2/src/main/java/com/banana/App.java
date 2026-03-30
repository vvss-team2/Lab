package com.banana;
import java.util.Scanner;

public class App {

    /**
     * Counts the number of even natural numbers in an array.
     * * @param n The expected number of elements.
     * @param valuesArray The array of numbers to check.
     * @return The count of even numbers.
     * @throws IllegalArgumentException if constraints are violated.
     */
    public static int countEvenNumbers(int n, int[] valuesArray) {
        // Validation for Req_EC 1.0 & 2.0: Natural numbers and array length
        if (n < 0) {
            throw new IllegalArgumentException("Error: n must be a natural number (>= 0).");
        }

        if (valuesArray == null || valuesArray.length != n) {
            throw new IllegalArgumentException("Error: Array length does not match n.");
        }

        int count = 0;
        for (int num : valuesArray) {
            // Validation for natural numbers (Req_EC 1.0: input < 0 is invalid)
            if (num < 0) {
                throw new IllegalArgumentException("Error: Array contains non-natural number: " + num);
            }

            // Logic for even numbers
            if (num % 2 == 0) {
                count++;
            }
        }
        return count;
    }

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        try {
            System.out.print("Enter number of elements (n): ");
            if (!scanner.hasNextInt()) {
                System.out.println("Error: Input is not an integer.");
                return;
            }
            int n = scanner.nextInt();

            int[] arr = new int[n];
            System.out.println("Enter " + n + " natural numbers:");
            for (int i = 0; i < n; i++) {
                if (!scanner.hasNextInt()) {
                    System.out.println("Error: All inputs must be integers.");
                    return;
                }
                arr[i] = scanner.nextInt();
            }

            int result = countEvenNumbers(n, arr);
            System.out.println("Result: " + result);

        } catch (IllegalArgumentException e) {
            System.out.println(e.getMessage());
        } catch (NegativeArraySizeException e) {
            System.out.println("Error: Array size cannot be negative.");
        } finally {
            scanner.close();
        }
    }
}