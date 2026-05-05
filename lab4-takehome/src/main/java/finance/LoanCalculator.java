package finance;

/**
 * Finance application: computes daily simple interest on a loan.
 *
 * Interest formula: I = P * r * (days / 365.0)
 * where P = principal, r = annual rate (e.g. 0.05 for 5%), days = days between
 * start and end.
 */
public class LoanCalculator {

    private final DateCalculator dateCalculator;

    public LoanCalculator(DateCalculator dateCalculator) {
        this.dateCalculator = dateCalculator;
    }

    /**
     * Returns the simple interest accrued between two dates.
     *
     * @param principal  loan amount
     * @param annualRate annual interest rate as a decimal (e.g. 0.05 = 5%)
     * @param d1,        m1, y1 loan start date
     * @param d2,        m2, y2 loan end date
     */
    public double computeInterest(double principal, double annualRate,
            int d1, int m1, int y1,
            int d2, int m2, int y2) {
        int days = dateCalculator.daysBetween2Dates(d1, m1, y1, d2, m2, y2);
        return principal * annualRate * (days / 365.0);
    }
}
