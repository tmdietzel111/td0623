package org.example.util;

import org.example.model.RentalAgreement;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.util.List;

public class DayCountingUtil {

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

	public static int countHolidays(RentalAgreement rentalAgreement) {

		LocalDate startDate = rentalAgreement.getCheckoutDate();
		LocalDate endDate = rentalAgreement.getDueDate();

		int holidayCount = 0;
		int startYear = startDate.getYear();
		int endYear = endDate.getYear();

		for (int year = startYear; year <= endYear; year++) {
			List<LocalDate> holidays = HolidayUtil.getHolidaysForYear(year);
			System.out.println("counting Holidays ");
			for (LocalDate holiday : holidays) {
				System.out.println(holiday);
				if (!startDate.isBefore(holiday) && !endDate.isAfter(holiday)) {
					holidayCount++;
				}
			}

		}
		return holidayCount;
	}


}
