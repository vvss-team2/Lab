package finance;

/**
 * Computes the number of days between two calendar dates.
 * Depends on a LeapYearChecker to correctly account for leap years.
 */
public class DateCalculator {

    private static final int[] DAYS_IN_MONTH = { 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 };

    private final LeapYearChecker leapYearChecker;

    public DateCalculator(LeapYearChecker leapYearChecker) {
        this.leapYearChecker = leapYearChecker;
    }

    /**
     * Returns the absolute number of days between (d1,m1,y1) and (d2,m2,y2).
     */
    public int daysBetween2Dates(int d1, int m1, int y1, int d2, int m2, int y2) {
        long abs1 = toDayCount(d1, m1, y1);
        long abs2 = toDayCount(d2, m2, y2);
        return (int) Math.abs(abs2 - abs1);
    }

    private long toDayCount(int d, int m, int y) {
        long days = d;

        for (int i = 0; i < m - 1; i++) {
            days += DAYS_IN_MONTH[i];
        }

        if (m > 2 && leapYearChecker.isLeapYear(y)) {
            days += 1;
        }

        for (int i = 0; i < y; i++) {
            days += leapYearChecker.isLeapYear(i) ? 366 : 365;
        }

        return days;
    }
}
