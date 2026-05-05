package finance;

import org.junit.Test;
import org.junit.Before;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

/**
 * Unit tests for IsLeapYear using Mockito mocks.
 *
 * The mock verifies that callers interact with LeapYearChecker correctly
 * and that the real implementation returns expected values.
 */
public class IsLeapYearMockTest {

    private LeapYearChecker mockLeapYearChecker;

    @Before
    public void setUp() {
        mockLeapYearChecker = mock(LeapYearChecker.class);
    }

    // --- Mock behaviour tests (stub-style verification) ---

    @Test
    public void mock_leapYear_divisibleBy400_returnsTrue() {
        when(mockLeapYearChecker.isLeapYear(2000)).thenReturn(true);
        assertTrue(mockLeapYearChecker.isLeapYear(2000));
        verify(mockLeapYearChecker).isLeapYear(2000);
    }

    @Test
    public void mock_leapYear_divisibleBy4NotBy100_returnsTrue() {
        when(mockLeapYearChecker.isLeapYear(2024)).thenReturn(true);
        assertTrue(mockLeapYearChecker.isLeapYear(2024));
    }

    @Test
    public void mock_leapYear_divisibleBy100NotBy400_returnsFalse() {
        when(mockLeapYearChecker.isLeapYear(1900)).thenReturn(false);
        assertFalse(mockLeapYearChecker.isLeapYear(1900));
    }

    @Test
    public void mock_leapYear_commonYear_returnsFalse() {
        when(mockLeapYearChecker.isLeapYear(2023)).thenReturn(false);
        assertFalse(mockLeapYearChecker.isLeapYear(2023));
    }

    // --- Real implementation tests ---

    @Test
    public void real_isLeapYear_2000_returnsTrue() {
        LeapYearChecker checker = new LeapYearCheckerImpl();
        assertTrue(checker.isLeapYear(2000));
    }

    @Test
    public void real_isLeapYear_2024_returnsTrue() {
        LeapYearChecker checker = new LeapYearCheckerImpl();
        assertTrue(checker.isLeapYear(2024));
    }

    @Test
    public void real_isLeapYear_1900_returnsFalse() {
        LeapYearChecker checker = new LeapYearCheckerImpl();
        assertFalse(checker.isLeapYear(1900));
    }

    @Test
    public void real_isLeapYear_2023_returnsFalse() {
        LeapYearChecker checker = new LeapYearCheckerImpl();
        assertFalse(checker.isLeapYear(2023));
    }

    @Test
    public void real_isLeapYear_year0_returnsTrue() {
        // Year 0 % 400 == 0
        LeapYearChecker checker = new LeapYearCheckerImpl();
        assertTrue(checker.isLeapYear(0));
    }
}
