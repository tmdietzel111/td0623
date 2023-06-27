package org.example.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class HolidayUtil {

	private static final Map<Integer, List<LocalDate>> cachedHolidays = new HashMap<>();


	public static List<LocalDate> getHolidaysForYear(int year) {
		if (!cachedHolidays.containsKey(year)) {
			List<LocalDate> holidays = new ArrayList<>();

			holidays.add(getForthOfJulyHoliday(year));
			holidays.add(getLaborDay(year));

			cachedHolidays.put(year, holidays);
		}
		return cachedHolidays.get(year);
	}

	private static LocalDate getForthOfJulyHoliday(int year) {
		LocalDate forthOfJuly = LocalDate.of(year, 7, 4);
		//Dumb way to do it but functional
		if (forthOfJuly.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return forthOfJuly.minusDays(1);
		}
		if (forthOfJuly.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			return forthOfJuly.plusDays(1);
		}

		return forthOfJuly;
	}

	private static LocalDate getLaborDay(int year) {
		LocalDate laborDay = LocalDate.of(year, 9, 1);

		//More dumb solutions
		while (!DayOfWeek.MONDAY.equals(laborDay.getDayOfWeek())) {
			laborDay = laborDay.plusDays(1);
		}

		return laborDay;
	}

}
