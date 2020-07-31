package utils;

import java.time.LocalDate;

public class DateParser {
    public static LocalDate parseDateFromCucumber(String date) {
        if (isCurrentDate(date)) {
            return LocalDate.now();
        } else if (isDateInTheFuture(date)) {
            return LocalDate.now().plusMonths(1);
        } else if (isDateInThePast(date)) {
            return LocalDate.now().minusMonths(1);
        } else {
            return LocalDate.parse(date);
        }
    }

    private static boolean isCurrentDate(String date) {
        return date.toUpperCase().startsWith("CURRENT");
    }

    private static boolean isDateInTheFuture(String date) {
        return date.toUpperCase().startsWith("FUTURE");
    }

    private static boolean isDateInThePast(String date) {
        return date.toUpperCase().startsWith("PAST");
    }
}
