package org.example.service;

import org.example.model.Checkout;
import org.example.model.RentalAgreement;
import org.example.repository.ToolRepository;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class CheckoutServiceTest {

	//This should be autowired but right now, not needed
	CheckoutService checkoutService = new CheckoutService(new ToolRepository());

	/**
	 * This one should pass validation, so we're going to validate it directly on the service
	 * LADW is a Ladder, so no holiday charge, 1.99 a day
	 * <p>
	 * 7/2/20 for 3 days is Thursday Friday Saturday, one day off so 2 days at 1.99 is 3.98 base charge
	 * 10% discount is 0.398 ~ .40 so  expect 3.58 final charge
	 */
	@Test
	public void test2() {
		Checkout checkout = new Checkout("LADW", 3, LocalDate.of(2020, 7, 3), 10);

		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);

		assertNotNull(rentalAgreement);
		int chargeDays = 2;
		BigDecimal preDiscountCharge = new BigDecimal("3.98");
		BigDecimal discountAmount = new BigDecimal("0.40");
		BigDecimal finalAmount = new BigDecimal("3.58");

		assertEquals(chargeDays, rentalAgreement.getChargeDays(), "Charge Days not equal to 2 for test case 2");
		assertEquals(preDiscountCharge, rentalAgreement.getPreDiscountCharge(), "Pre Discount Amount not equal to 3.98");
		assertEquals(discountAmount, rentalAgreement.getDiscountAmount(), "Discount amount not equal to .40");
		assertEquals(finalAmount, rentalAgreement.getFinalCharge(), "Final Charge not equal to 3.58");
	}

	/**
	 * This one should pass validation, so we're going to validate it directly on the service
	 * CHNS is a Chainsaw, so no weekend charge, 1.49 a day
	 * <p>
	 * 7/2/15 for 5 days is Thursday Friday Saturday, two day off so 3 days at 1.49 is 4.47 base charge
	 * 25% discount is 1.1175 ~ 1.12 so  expect 3.35 final charge
	 */
	@Test
	public void test3() {
		Checkout checkout = new Checkout("CHNS", 5, LocalDate.of(2015, 7, 2), 25);

		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);

		assertNotNull(rentalAgreement);
		int chargeDays = 3;
		BigDecimal preDiscountCharge = new BigDecimal("4.47");
		BigDecimal discountAmount = new BigDecimal("1.12");
		BigDecimal finalAmount = new BigDecimal("3.35");

		assertEquals(chargeDays, rentalAgreement.getChargeDays(), "Charge Days not equal to 3 for test case 3");
		assertEquals(preDiscountCharge, rentalAgreement.getPreDiscountCharge(), "Pre Discount Amount not equal to 4.47");
		assertEquals(discountAmount, rentalAgreement.getDiscountAmount(), "Discount amount not equal to 1.12");
		assertEquals(finalAmount, rentalAgreement.getFinalCharge(), "Final Charge not equal to 3.35");
	}

	/**
	 * This one should pass validation, so we're going to validate it directly on the service
	 * JAKD is a Jackhammer, so no weekend charge or  Holiday, 2.99 a day
	 * <p>
	 * 9/3/15 for 3 days is Thursday through Wednesday, including Labor Day so 3 days at 2.99 is 8.97 base charge
	 * 0% discount is 0 so  expect 8.97 final charge
	 */
	@Test
	public void test4() {
		Checkout checkout = new Checkout("JAKD", 6, LocalDate.of(2015, 9, 3), 0);

		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);

		assertNotNull(rentalAgreement);
		int chargeDays = 3;
		BigDecimal preDiscountCharge = new BigDecimal("8.97");
		BigDecimal discountAmount = new BigDecimal("0.00");
		BigDecimal finalAmount = new BigDecimal("8.97");

		assertEquals(chargeDays, rentalAgreement.getChargeDays(), "Charge Days not equal to 3 for test case 4");
		assertEquals(preDiscountCharge, rentalAgreement.getPreDiscountCharge(), "Pre Discount Amount not equal to 8.97");
		assertEquals(discountAmount, rentalAgreement.getDiscountAmount(), "Discount amount not equal to 0.00");
		assertEquals(finalAmount, rentalAgreement.getFinalCharge(), "Final Charge not equal to 8.97");
	}

	/**
	 * This one should pass validation, so we're going to validate it directly on the service
	 * JAKR is a Jackhammer, so no weekend charge or  Holiday, 2.99 a day
	 * <p>
	 * 7/2/15 for 9 days is Thursday through Saturday, including 4th of July so 6 days at 2.99 is 17.94 base charge
	 * 0% discount is 0 so  expect 17.94 final charge
	 */
	@Test
	public void test5() {
		Checkout checkout = new Checkout("JAKR", 9, LocalDate.of(2015, 7, 2), 0);

		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);

		assertNotNull(rentalAgreement);
		int chargeDays = 6;
		BigDecimal preDiscountCharge = new BigDecimal("17.94");
		BigDecimal discountAmount = new BigDecimal("0.00");
		BigDecimal finalAmount = new BigDecimal("17.94");

		assertEquals(chargeDays, rentalAgreement.getChargeDays(), "Charge Days not equal to 6 for test case 4");
		assertEquals(preDiscountCharge, rentalAgreement.getPreDiscountCharge(), "Pre Discount Amount not equal to 17.94");
		assertEquals(discountAmount, rentalAgreement.getDiscountAmount(), "Discount amount not equal to 0.00");
		assertEquals(finalAmount, rentalAgreement.getFinalCharge(), "Final Charge not equal to 17.94");
	}

	/**
	 * This one should pass validation, so we're going to validate it directly on the service
	 * JAKR is a Jackhammer, so no weekend charge or  Holiday, 2.99 a day
	 * <p>
	 * 7/2/20 for 4 days is Thursday through Sunday, including 4th of July so 1 day at 2.99 is 2.99 base charge
	 * 50% discount is 1.495 ~ 1.50 so  expect 1.49 final charge
	 */
	@Test
	public void test6() {
		Checkout checkout = new Checkout("JAKR", 4, LocalDate.of(2020, 7, 2), 50);

		RentalAgreement rentalAgreement = checkoutService.getRentalAgreement(checkout);

		assertNotNull(rentalAgreement);
		int chargeDays = 1;
		BigDecimal preDiscountCharge = new BigDecimal("2.99");
		BigDecimal discountAmount = new BigDecimal("1.50");
		BigDecimal finalAmount = new BigDecimal("1.49");

		assertEquals(chargeDays, rentalAgreement.getChargeDays(), "Charge Days not equal to 1 for test case 6");
		assertEquals(preDiscountCharge, rentalAgreement.getPreDiscountCharge(), "Pre Discount Amount not equal to 2.99");
		assertEquals(discountAmount, rentalAgreement.getDiscountAmount(), "Discount amount not equal to 1.50");
		assertEquals(finalAmount, rentalAgreement.getFinalCharge(), "Final Charge not equal to 1.49");
	}
}
