package finance;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Driver tests for DaysBetween2Dates using IsLeapYear as a stub (Mockito mock).
 *
 * The stub replaces the real LeapYearChecker so DaysBetween2Dates can be
 * tested in isolation, exercising specific leap-year code paths without
 * depending on the real implementation.
 */
public class DateCalculatorDriverTest {

    private LeapYearChecker stubLeapYearChecker;
    private DateCalculator dateCalculator;

    @Before
    public void setUp() {
        stubLeapYearChecker = mock(LeapYearChecker.class);
        dateCalculator = new DateCalculator(stubLeapYearChecker);
    }

    /**
     * Same date → 0 days regardless of leap year.
     */
    @Test
    public void driver_sameDate_returns0() {
        // stub: no particular leap-year behaviour needed for same-date call,
        // but the internal loop still queries years 0..2024 → stub them all as false
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);

        int result = dateCalculator.daysBetween2Dates(15, 6, 2024, 15, 6, 2024);
        assertEquals(0, result);
    }

    /**
     * 1 Jan 2023 → 1 Jan 2024: non-leap year → 365 days.
     * Stub: year 2023 is NOT a leap year.
     */
    @Test
    public void driver_fullNonLeapYear_returns365() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2023)).thenReturn(false);

        int result = dateCalculator.daysBetween2Dates(1, 1, 2023, 1, 1, 2024);
        assertEquals(365, result);
    }

    /**
     * 1 Jan 2024 → 1 Jan 2025: leap year → 366 days.
     * Stub: year 2024 IS a leap year.
     */
    @Test
    public void driver_fullLeapYear_returns366() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2024)).thenReturn(true);

        int result = dateCalculator.daysBetween2Dates(1, 1, 2024, 1, 1, 2025);
        assertEquals(366, result);
    }

    /**
     * 28 Feb → 1 Mar in a NON-leap year → 1 day.
     * Stub: year 2023 is NOT a leap year.
     */
    @Test
    public void driver_feb28ToMar1_nonLeapYear_returns1() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2023)).thenReturn(false);

        int result = dateCalculator.daysBetween2Dates(28, 2, 2023, 1, 3, 2023);
        assertEquals(1, result);
    }

    /**
     * 28 Feb → 1 Mar in a LEAP year → 2 days (Feb has 29 days).
     * Stub: year 2024 IS a leap year.
     */
    @Test
    public void driver_feb28ToMar1_leapYear_returns2() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2024)).thenReturn(true);

        int result = dateCalculator.daysBetween2Dates(28, 2, 2024, 1, 3, 2024);
        assertEquals(2, result);
    }

    /**
     * Date order is swapped → result is still positive (absolute value).
     */
    @Test
    public void driver_reversedDates_returnsPositive() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);

        int forward = dateCalculator.daysBetween2Dates(1, 1, 2023, 31, 12, 2023);
        int backward = dateCalculator.daysBetween2Dates(31, 12, 2023, 1, 1, 2023);
        assertEquals(forward, backward);
    }

    /**
     * Verify stub was actually consulted (interaction verification).
     */
    @Test
    public void driver_verifyLeapYearCheckerIsInvoked() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);

        dateCalculator.daysBetween2Dates(1, 3, 2020, 1, 4, 2020);

        verify(stubLeapYearChecker, atLeastOnce()).isLeapYear(anyInt());
    }
}
