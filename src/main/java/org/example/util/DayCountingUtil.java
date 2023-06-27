package org.example.util;

import org.example.model.RentalAgreement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

/**
 * Utility class for the day counting logic. Currently uses Rental Agreements rather than fields of a rental agreement -
 */
public class DayCountingUtil {

	/**
	 * Counts the weekends
	 * <p>
	 * Logic - first, divide rental days by 7 and multiply than number by 2 - there are 2 weekends for every 7 days
	 * <p>
	 * Once we have that, modulo the remainder. starting with the starting day of the week, check every day and see if its a weekend
	 * <p>
	 * Second step could be cleaned up, but its <6 steps worse case so we'll be okay
	 *
	 * @param rentalAgreement
	 * @return
	 */
	public static int countWeekends(RentalAgreement rentalAgreement) {

		int rentalDays = rentalAgreement.getRentalDays();
		LocalDate startDate = rentalAgreement.getCheckoutDate();
		//There are two weekend days for every seven days
		int weekendDays = rentalDays * 2 / 7;

		int remainingDays = rentalDays % 7;

		//easier to just count forward than to figure out
		for (int i = 0; i < remainingDays; i++) {
			DayOfWeek dayOfWeek = startDate.plusDays(i).getDayOfWeek();
			if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
				weekendDays++;
			}
		}

		return weekendDays;
	}

	/**
	 * Counts the holidays in the rental agreement
	 * <p>
	 * Logic - Identify all the years relevant to the rental agreement
	 * <p>
	 * Get the holidays from the Utility for that year, determine if its between the two days and add it if so
	 *
	 * @param rentalAgreement
	 * @return count of holidays in the rental agreement
	 */
	public static int countHolidays(RentalAgreement rentalAgreement) {

		LocalDate startDate = rentalAgreement.getCheckoutDate();
		LocalDate endDate = rentalAgreement.getDueDate();

		int holidayCount = 0;
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();

		for (int year = startYear; year <= endYear; year++) {
			List<LocalDate> holidays = HolidayUtil.getHolidaysForYear(year);
			for (LocalDate holiday : holidays) {
				//If the holiday isn't before the start date and isn't after the end date its between them
				if (!holiday.isBefore(startDate) && !holiday.isAfter(endDate)) {
					holidayCount++;
				}
			}

		}
		return holidayCount;
	}


}
