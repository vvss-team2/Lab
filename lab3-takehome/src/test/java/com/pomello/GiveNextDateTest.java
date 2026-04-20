package com.pomello;

import static org.junit.Assert.*;
import org.junit.Test;

public class GiveNextDateTest {

    // --- Invalid inputs ---

    @Test
    public void testDayLessThanOne() {
        assertEquals("invalid Input Date", new GiveNextDate(1, 0, 2000).run());
    }

    @Test
    public void testMonthLessThanOne() {
        assertEquals("invalid Input Date", new GiveNextDate(0, 1, 2000).run());
    }

    @Test
    public void testMonthGreaterThan12() {
        assertEquals("invalid Input Date", new GiveNextDate(13, 1, 2000).run());
    }

    @Test
    public void testYearLessThan1801() {
        assertEquals("invalid Input Date", new GiveNextDate(1, 1, 1800).run());
    }

    @Test
    public void testYearGreaterThan2021() {
        assertEquals("invalid Input Date", new GiveNextDate(1, 1, 2022).run());
    }

    // --- 31-day months (1, 3, 5, 8, 10) ---

    @Test
    public void testMonth1_dayLessThan31() {
        assertEquals("1/16/2000", new GiveNextDate(1, 15, 2000).run());
    }

    @Test
    public void testMonth1_day31() {
        assertEquals("2/1/2000", new GiveNextDate(1, 31, 2000).run());
    }

    @Test
    public void testMonth3_dayLessThan31() {
        assertEquals("3/16/2000", new GiveNextDate(3, 15, 2000).run());
    }

    @Test
    public void testMonth5_day31() {
        assertEquals("6/1/2000", new GiveNextDate(5, 31, 2000).run());
    }

    @Test
    public void testMonth8_dayLessThan31() {
        assertEquals("8/16/2000", new GiveNextDate(8, 15, 2000).run());
    }

    @Test
    public void testMonth10_day31() {
        assertEquals("11/1/2000", new GiveNextDate(10, 31, 2000).run());
    }

    // --- 30-day months (4, 6, 9, 11) ---

    @Test
    public void testMonth4_dayLessThan30() {
        assertEquals("4/16/2000", new GiveNextDate(4, 15, 2000).run());
    }

    @Test
    public void testMonth4_day30() {
        assertEquals("5/1/2000", new GiveNextDate(4, 30, 2000).run());
    }

    @Test
    public void testMonth4_dayGreaterThan30() {
        assertEquals("Invalid Input Date", new GiveNextDate(4, 31, 2000).run());
    }

    @Test
    public void testMonth6_dayLessThan30() {
        assertEquals("6/16/2000", new GiveNextDate(6, 15, 2000).run());
    }

    @Test
    public void testMonth9_dayLessThan30() {
        assertEquals("9/16/2000", new GiveNextDate(9, 15, 2000).run());
    }

    @Test
    public void testMonth11_day30() {
        assertEquals("12/1/2000", new GiveNextDate(11, 30, 2000).run());
    }

    // --- December ---

    @Test
    public void testDecember_dayLessThanOrEqualTo31() {
        assertEquals("12/16/2000", new GiveNextDate(12, 15, 2000).run());
    }

    @Test
    public void testDecember_dayGreaterThan31_year2021() {
        assertEquals("Invalid Next Year", new GiveNextDate(12, 32, 2021).run());
    }

    @Test
    public void testDecember_dayGreaterThan31_notYear2021() {
        assertEquals("1/1/2001", new GiveNextDate(12, 32, 2000).run());
    }

    // --- February ---

    @Test
    public void testFebruary_dayLessThan28() {
        assertEquals("2/16/2000", new GiveNextDate(2, 15, 2000).run());
    }

    @Test
    public void testFebruary_day28_leapYear_century() {
        // 2000: divisible by 400 -> leap year
        assertEquals("2/29/2000", new GiveNextDate(2, 28, 2000).run());
    }

    @Test
    public void testFebruary_day28_notLeapYear_century() {
        // 1900: divisible by 100 but not 400 -> not leap year
        assertEquals("3/1/1900", new GiveNextDate(2, 28, 1900).run());
    }

    @Test
    public void testFebruary_day28_leapYear_nonCentury() {
        // 2004: divisible by 4 but not 100 -> leap year
        assertEquals("2/29/2004", new GiveNextDate(2, 28, 2004).run());
    }

    @Test
    public void testFebruary_day28_notLeapYear_nonCentury() {
        // 2001: not divisible by 4 -> not leap year
        assertEquals("3/1/2001", new GiveNextDate(2, 28, 2001).run());
    }

    @Test
    public void testFebruary_day29_leapYear() {
        assertEquals("3/1/2000", new GiveNextDate(2, 29, 2000).run());
    }

    @Test
    public void testFebruary_day29_notLeapYear() {
        assertEquals("Invalid Input Date", new GiveNextDate(2, 29, 2001).run());
    }

    @Test
    public void testFebruary_dayGreaterThan29() {
        assertEquals("Invalid Input Date", new GiveNextDate(2, 30, 2000).run());
    }

    // --- Month with no explicit handler (e.g., July = 7) covers isFebruary false
    // branch ---

    @Test
    public void testMonth7_noHandler() {
        // July is not in any of the four handlers; values are returned unchanged
        assertEquals("7/15/2000", new GiveNextDate(7, 15, 2000).run());
    }

    // --- main method ---

    @Test
    public void testMain() {
        GiveNextDate.main(new String[] {});
    }
}
