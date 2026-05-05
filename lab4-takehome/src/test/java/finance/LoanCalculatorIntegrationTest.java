package finance;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.mockito.Mockito.*;

/**
 * Integration tests simulating the Finance Systems (Loan / Interest
 * Calculation) application.
 *
 * Scenario: A bank computes daily simple interest on a loan.
 * I = P * r * (days / 365.0)
 *
 * Two integration modes are tested:
 * 1. With a STUB (mocked LeapYearChecker) injected into DateCalculator
 * → tests DaysBetween2Dates in isolation from the real IsLeapYear.
 * 2. With the REAL LeapYearCheckerImpl end-to-end.
 */
public class LoanCalculatorIntegrationTest {

    // --- integration with stub (mock) ---

    private LeapYearChecker stubLeapYearChecker;
    private LoanCalculator loanCalculatorWithStub;

    // --- integration with real impl ---

    private LoanCalculator loanCalculatorReal;

    private static final double DELTA = 0.01;

    @Before
    public void setUp() {
        stubLeapYearChecker = mock(LeapYearChecker.class);
        loanCalculatorWithStub = new LoanCalculator(new DateCalculator(stubLeapYearChecker));

        loanCalculatorReal = new LoanCalculator(new DateCalculator(new LeapYearCheckerImpl()));
    }

    // -------------------------------------------------------------------------
    // Tests with stub (mocked IsLeapYear)
    // -------------------------------------------------------------------------

    /**
     * Loan from 1 Jan 2023 to 1 Jan 2024 (non-leap, 365 days).
     * Principal = 10000, rate = 5% → expected interest = 500.00
     */
    @Test
    public void stub_loan365Days_5Percent_returns500() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2023)).thenReturn(false);

        double interest = loanCalculatorWithStub.computeInterest(10000, 0.05,
                1, 1, 2023, 1, 1, 2024);
        assertEquals(500.00, interest, DELTA);
    }

    /**
     * Loan during a leap year (1 Jan 2024 → 1 Jan 2025, 366 days).
     * Stub tells isLeapYear(2024) = true.
     * Principal = 10000, rate = 5% → expected interest = 10000 * 0.05 * (366/365) ≈
     * 501.37
     *
     * Typical bug: if leap year is ignored → 365 days → interest = 500.00 (off by
     * one day).
     */
    @Test
    public void stub_loanLeapYear366Days_5Percent_greaterThan500() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2024)).thenReturn(true);

        double interest = loanCalculatorWithStub.computeInterest(10000, 0.05,
                1, 1, 2024, 1, 1, 2025);
        double expected = 10000 * 0.05 * (366.0 / 365.0);
        assertEquals(expected, interest, DELTA);
    }

    /**
     * Short loan: 1 Jan 2023 → 31 Jan 2023 (30 days).
     * Principal = 5000, rate = 12% → I = 5000 * 0.12 * (30/365) ≈ 49.32
     */
    @Test
    public void stub_loan30Days_12Percent() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);

        double interest = loanCalculatorWithStub.computeInterest(5000, 0.12,
                1, 1, 2023, 31, 1, 2023);
        double expected = 5000 * 0.12 * (30.0 / 365.0);
        assertEquals(expected, interest, DELTA);
    }

    /**
     * Loan that crosses Feb 29 in a leap year (1 Feb 2024 → 1 Mar 2024 = 29 days).
     * Stub: isLeapYear(2024) = true.
     * Principal = 12000, rate = 6%.
     * Expected: 12000 * 0.06 * (29/365) ≈ 57.37
     *
     * Typical bug: if Feb is always 28 days → 28 days → interest ≈ 55.40
     * (under-charges by 1 day).
     */
    @Test
    public void stub_loanAcrossLeapDay_correctDayCount() {
        when(stubLeapYearChecker.isLeapYear(anyInt())).thenReturn(false);
        when(stubLeapYearChecker.isLeapYear(2024)).thenReturn(true);

        double interest = loanCalculatorWithStub.computeInterest(12000, 0.06,
                1, 2, 2024, 1, 3, 2024);
        double expected = 12000 * 0.06 * (29.0 / 365.0);
        assertEquals(expected, interest, DELTA);
    }

    // -------------------------------------------------------------------------
    // End-to-end tests with real LeapYearCheckerImpl
    // -------------------------------------------------------------------------

    /**
     * Real impl: 1 Jan 2020 → 1 Jan 2021 (2020 is a real leap year, 366 days).
     */
    @Test
    public void real_loan2020LeapYear_366Days() {
        double interest = loanCalculatorReal.computeInterest(10000, 0.05,
                1, 1, 2020, 1, 1, 2021);
        double expected = 10000 * 0.05 * (366.0 / 365.0);
        assertEquals(expected, interest, DELTA);
    }

    /**
     * Real impl: 1 Jan 1900 → 1 Jan 1901 (1900 is NOT a leap year, 365 days).
     * Common mistake: treating century years as leap years.
     */
    @Test
    public void real_loan1900NotLeapYear_365Days() {
        double interest = loanCalculatorReal.computeInterest(10000, 0.05,
                1, 1, 1900, 1, 1, 1901);
        double expected = 10000 * 0.05 * (365.0 / 365.0);
        assertEquals(expected, interest, DELTA);
    }
}
