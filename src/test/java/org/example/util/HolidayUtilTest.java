package org.example.util;

import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class HolidayUtilTest {

	@Test
	void verifyHolidaysFor2023() {
		int year = 2023;
		List<LocalDate> holidays = HolidayUtil.getHolidaysForYear(year);

		//July 4th is on a Tuesday
		//Labor Day is September 4th

		LocalDate fourthOfJuly = LocalDate.of(year, 7, 4);
		LocalDate laborDay = LocalDate.of(year, 9, 4);

		assertTrue(holidays.contains(fourthOfJuly), "Fourth of July not found in Holiday Array for " + year);
		assertTrue(holidays.contains(laborDay), "Labor Day not found in Holiday array for September 4, 2023");
	}

	@Test
	void verifyHolidaysFor2026() {
		int year = 2026;
		List<LocalDate> holidays = HolidayUtil.getHolidaysForYear(year);

		//July 4th is on a Saturday, so expect it on the 3rd
		//Labor Day is September 7th
		LocalDate fourthOfJuly = LocalDate.of(year, 7, 3);
		LocalDate laborDay = LocalDate.of(year, 9, 7);

		assertTrue(holidays.contains(fourthOfJuly), "Fourth of July not found in Holiday Array for " + year);
		assertTrue(holidays.contains(laborDay), "Labor Day not found in Holiday array for September 7, 2026");
	}

	@Test
	void verifyHolidaysFor2021() {
		int year = 2021;
		List<LocalDate> holidays = HolidayUtil.getHolidaysForYear(year);

		//July 4th is on a Sunday, so expect it on the 5th
		//Labor Day is September 7th
		LocalDate fourthOfJuly = LocalDate.of(year, 7, 5);
		LocalDate laborDay = LocalDate.of(year, 9, 6);

		assertTrue(holidays.contains(fourthOfJuly), "Fourth of July not found in Holiday Array for " + year);
		assertTrue(holidays.contains(laborDay), "Labor Day not found in Holiday array for September 6, 2021");
	}
}


