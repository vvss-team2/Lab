package com.demo;

/**
 * Utility class for basic string operations.
 */
public class StringUtils {

    /**
     * Reverses a string.
     * @throws IllegalArgumentException if input is null
     */
    public String reverse(String s) {
        if (s == null) throw new IllegalArgumentException("Input cannot be null");
        return new StringBuilder(s).reverse().toString();
    }

    /**
     * Counts how many times a character appears in a string.
     */
    public int countChar(String s, char c) {
        if (s == null) return 0;
        int count = 0;
        for (char ch : s.toCharArray()) {
            if (ch == c) count++;
        }
        return count;
    }

    /**
     * Checks if a string is a palindrome (ignores case and spaces).
     */
    public boolean isPalindrome(String s) {
        if (s == null) return false;
        String cleaned = s.toLowerCase().replaceAll("\\s+", "");
        String reversed = new StringBuilder(cleaned).reverse().toString();
        return cleaned.equals(reversed);
    }

    /**
     * Capitalizes the first letter of each word.
     */
    public String titleCase(String s) {
        if (s == null || s.isEmpty()) return s;
        String[] words = s.split("\\s+");
        StringBuilder sb = new StringBuilder();
        for (String word : words) {
            if (!word.isEmpty()) {
                sb.append(Character.toUpperCase(word.charAt(0)))
                  .append(word.substring(1).toLowerCase())
                  .append(" ");
            }
        }
        return sb.toString().trim();
    }

    /**
     * Returns true if the string contains only digits.
     */
    public boolean isNumeric(String s) {
        if (s == null || s.isEmpty()) return false;
        for (char c : s.toCharArray()) {
            if (!Character.isDigit(c)) return false;
        }
        return true;
    }

    /**
     * Truncates a string to maxLength, appending "..." if truncated.
     */
    public String truncate(String s, int maxLength) {
        if (s == null) return null;
        if (s.length() <= maxLength) return s;
        return s.substring(0, maxLength) + "...";
    }
}
