public class Solution {
    public static boolean isLeapYear(int year) {
        return (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0);
    }


    public static int daysBetween2Dates(int d1, int m1, int y1,
                                    int d2, int m2, int y2) {

        int[] daysInMonth = {31, 28, 31, 30, 31, 30,
                            31, 31, 30, 31, 30, 31};

        java.util.function.Function<int[], Integer> dateToDays = (date) -> {
            int d = date[0], m = date[1], y = date[2];
            int days = d;

            for (int i = 0; i < m - 1; i++) {
                days += daysInMonth[i];
            }

            if (m > 2 && isLeapYear(y)) {
                days += 1;
            }

            for (int i = 0; i < y; i++) {
                days += isLeapYear(i) ? 366 : 365;
            }

            return days;
        };

        int days1 = dateToDays.apply(new int[]{d1, m1, y1});
        int days2 = dateToDays.apply(new int[]{d2, m2, y2});

        return Math.abs(days2 - days1);
    }

    public static void main(String[] args) {
        int result = daysBetween2Dates(1, 3, 2020, 1, 2, 2020);
        System.out.println("Days between dates: " + result);
    }
}