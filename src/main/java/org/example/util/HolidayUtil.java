package org.example.util;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Simple utility for calculating holidays for a year. Only 2 at the moment
 * <p>
 * Caches the responses for the year just to prevent re-calculating it every time
 */
public class HolidayUtil {

	private static final Map<Integer, List<LocalDate>> cachedHolidays = new HashMap<>();


	/**
	 * Gets any holidays in the passed in year
	 *
	 * @param year
	 * @return LocalDates of all observed holidays in that year
	 */
	public static List<LocalDate> getHolidaysForYear(int year) {
		if (!cachedHolidays.containsKey(year)) {
			List<LocalDate> holidays = new ArrayList<>();

			holidays.add(getForthOfJulyHoliday(year));
			holidays.add(getLaborDay(year));

			cachedHolidays.put(year, holidays);
		}
		return cachedHolidays.get(year);
	}

	/**
	 * Fourth of July calculation logic -
	 * <p>
	 * Its the fourth of July
	 * unless the 4th is a Saturday, in which case its the third of July
	 * or the 4th is a Sunday, in which case its the Fifth of July
	 *
	 * @param year
	 * @return
	 */
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

	/**
	 * Labor Day calculation
	 * <p>
	 * Start on September 1st, keep counting forward until you find a monday.
	 * <p>
	 * TODO find better way to do this but this works
	 *
	 * @param year
	 * @return
	 */
	private static LocalDate getLaborDay(int year) {
		LocalDate laborDay = LocalDate.of(year, 9, 1);

		//More dumb solutions
		while (!DayOfWeek.MONDAY.equals(laborDay.getDayOfWeek())) {
			laborDay = laborDay.plusDays(1);
		}

		return laborDay;
	}

}
