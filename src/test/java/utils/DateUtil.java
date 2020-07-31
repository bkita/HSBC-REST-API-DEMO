package utils;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class DateUtil {
    public static List<String> getCurrentDateRange(LocalDate date) {
        List<String> currentDateRange = new ArrayList<>();
        boolean range = false;

        if (date.isEqual(LocalDate.now()) || date.isAfter(LocalDate.now())) {
            date = LocalDate.now();
            range = true;
        }

        date = validateIsWeekend(date);
        date = validateIsHolidays(date);

        currentDateRange.add(date.toString());
        if (range) {
            currentDateRange.add(date.minusDays(1).toString());
        }

        return currentDateRange;
    }

    private static LocalDate validateIsWeekend(LocalDate date) {
        if (isSunday(date)) {
            return date.minusDays(2);
        } else if (isSaturday(date)) {
            return date.minusDays(1);
        } else {
            return date;
        }
    }

    private static LocalDate validateIsHolidays(LocalDate date) {
        if (getHolidays2020().contains(date.toString())) {
            if (date.getDayOfWeek().equals(DayOfWeek.MONDAY)) {
                return validateIsHolidays(date.minusDays(3));
            } else {
                return date.minusDays(1);
            }
        } else {
            return date;
        }
    }

    private static List<String> getHolidays2020() {
        List<String> holidays = new ArrayList<>();
        holidays.add("2020-01-01");
        holidays.add("2020-04-10");
        holidays.add("2020-04-13");
        holidays.add("2020-05-01");
        holidays.add("2020-05-01");
        holidays.add("2020-12-26");
        return holidays;
    }

    private static Boolean isSaturday(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SATURDAY);
    }

    private static Boolean isSunday(LocalDate date) {
        return date.getDayOfWeek().equals(DayOfWeek.SUNDAY);
    }
}
