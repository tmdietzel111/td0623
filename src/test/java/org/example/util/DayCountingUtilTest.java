package org.example.util;

import org.example.model.Checkout;
import org.example.model.RentalAgreement;
import org.example.model.RentalCharge;
import org.example.model.Tool;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DayCountingUtilTest {

	/**
	 * All we need is checkoutDate and rental Days
	 *
	 * @param checkoutDate Starting checkout date
	 * @param rentalDays   how many days to rent
	 * @return A rental agreement with just those populated
	 */
	private RentalAgreement buildRentalAgreement(LocalDate checkoutDate, int rentalDays) {
		Tool tool = new Tool();
		RentalCharge rentalCharge = new RentalCharge();
		Checkout checkout = new Checkout();
		checkout.setCheckoutDate(checkoutDate);
		checkout.setRentalDays(rentalDays);
		return new RentalAgreement(tool, rentalCharge, checkout);
	}

	@Test
	void verifyOneWeekWeekendCountIsTwo() {
		//Does not matter what today is, one week rental will be 2 weekends
		RentalAgreement rentalAgreement = buildRentalAgreement(LocalDate.now(), 7);

		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 2);
	}

	@Test
	void verifyTwoWeekWeekendCountIsFour() {
		//Does not matter what today is, one week rental will be 2 weekends
		RentalAgreement rentalAgreement = buildRentalAgreement(LocalDate.now(), 14);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 4);
	}

	@Test
	void verifyWeekendCountStartingAtMonday() {
		int rentalDays = 1;
		//6/26 is a monday - going to verify 1-7 have the correct days
		RentalAgreement rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 6, 26), rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 0, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 0, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 0, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 0, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 0, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 1, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");
		rentalAgreement.setRentalDays(++rentalDays);
		assertEquals(DayCountingUtil.countWeekends(rentalAgreement), 2, "Unexpected number of weekends starting on Monday for " + rentalDays + " rental Days");

	}

	@Test
	void verifyHolidayCountStartingAtFourth() {
		RentalAgreement rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 7, 4), 1);

		// One day and its a holiday, so one
		assertEquals(DayCountingUtil.countHolidays(rentalAgreement), 1, "Start day of a holiday not counted");
		//One days, not a holiday so zero
		rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 7, 3), 1);
		assertEquals(DayCountingUtil.countHolidays(rentalAgreement), 0, "Miscounted holiday");


		//Two days, second is a holiday, one day
		rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 7, 3), 2);
		assertEquals(DayCountingUtil.countHolidays(rentalAgreement), 1, "End day of a holiday not counted");

		//Three Days, holiday in the middle
		rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 7, 3), 3);
		assertEquals(DayCountingUtil.countHolidays(rentalAgreement), 1, "Middle day of a holiday not counted");
	}

	@Test
	void verifyMultiYearHolidayCount() {
		//6/26 is a monday - going to verify 1-7 have the correct days
		RentalAgreement rentalAgreement = buildRentalAgreement(LocalDate.of(2023, 1, 1), 730);
		//Two holidays, two years = 4 days
		assertEquals(DayCountingUtil.countHolidays(rentalAgreement), 4, "4 Holidays in two years");
	}
}
