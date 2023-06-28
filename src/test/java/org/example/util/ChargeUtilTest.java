package org.example.util;

import org.example.model.Checkout;
import org.example.model.RentalAgreement;
import org.example.model.RentalCharge;
import org.example.model.Tool;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ChargeUtilTest {


	private RentalAgreement buildRentalAgreement(int chargeDays, BigDecimal dailyRentalCharge) {
		Tool tool = new Tool();
		RentalCharge rentalCharge = new RentalCharge();
		rentalCharge.setDailyRate(dailyRentalCharge);
		Checkout checkout = new Checkout();
		checkout.setRentalDays(chargeDays);
		checkout.setDiscountPercent(0);
		checkout.setCheckoutDate(LocalDate.now());
		return new RentalAgreement(tool, rentalCharge, checkout);
	}

	//Really this is just verify standard bigDecimal math works - there's really no way to multiply a integer by 2 decimal points and get more
	@Test
	public void testBasicChargeAmount() {
		RentalAgreement rentalAgreement = buildRentalAgreement(2, new BigDecimal("1.99"));

		// Really basic, 2 * 1.99 = 3.98
		BigDecimal preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("3.98"), "Pre Discount Charge off expected values");

		rentalAgreement.setChargeDays(10);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("19.90"), "Pre Discount Charge off expected values");
	}

	// Assuming we make daily rental rates 3 digits of percision, lets verify our rounding for values ending in 5
	@Test
	public void testPercisionChargeAmountInFives() {
		//lets verify our rounding
		RentalAgreement rentalAgreement = buildRentalAgreement(1, new BigDecimal("1.005"));

		// Really basic, 1 * 1.005 = 1.005 rounds to 1.01
		BigDecimal preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("1.01"), "Pre Discount Charge off expected values for 1 day");


		// Really basic, 2 * 1.005 = 2.01
		rentalAgreement.setChargeDays(2);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("2.01"), "Pre Discount Charge off expected values for 2 days");

		// 10 * 1.005 = 10.05
		rentalAgreement.setChargeDays(10);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("10.05"), "Pre Discount Charge off expected values for 10 days");

		// 11 * 1.005 = 11.055 = 11.06
		rentalAgreement.setChargeDays(11);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("11.06"), "Pre Discount Charge off expected values for 11 days");
	}

	// Lets verify our rounding for non five numbers
	@Test
	public void testPercisionChargeAmountInFours() {
		//lets verify our rounding
		RentalAgreement rentalAgreement = buildRentalAgreement(1, new BigDecimal("1.004"));

		// Really basic, 1 * 1.004 = 1.004 rounds to 1.00
		BigDecimal preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("1.00"), "Pre Discount Charge did not round .004 down");

		// 2 * 1.004 = 2.008 = 2.01
		rentalAgreement.setChargeDays(2);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("2.01"), "Pre Discount Charge off expected values for 2 days");

		// 10 * 1.004 = 10.04
		rentalAgreement.setChargeDays(10);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("10.04"), "Pre Discount Charge off expected values for 10 days");

		// 11 * 1.004 = 11.044 = 11.04
		rentalAgreement.setChargeDays(11);
		preDiscount = ChargeUtil.calculatePreDiscountCharge(rentalAgreement);
		assertEquals(preDiscount, new BigDecimal("11.04"), "Pre Discount Charge off expected values for 11 days");
	}


	//Quick maths to verify our discount percentage rounds like we think it should
	@Test
	public void testDiscountPercentages() {
		RentalAgreement rentalAgreement = buildRentalAgreement(1, new BigDecimal("1.000"));
		//Easy number to do some basic calculations on
		rentalAgreement.setPreDiscountCharge(new BigDecimal("1234.56"));

		//Zero percent discount
		BigDecimal finalDiscount = ChargeUtil.calculateDiscountAmount(rentalAgreement);
		assertEquals(finalDiscount, new BigDecimal("0.00"), "Final Discount not zero for zero percent discount");

		rentalAgreement.setDiscountPercent(100);

		finalDiscount = ChargeUtil.calculateDiscountAmount(rentalAgreement);
		assertEquals(finalDiscount, new BigDecimal("1234.56"), "Final Discount for One Hundred percent discount not 1000.00");

		rentalAgreement.setDiscountPercent(17);
		// 17% of 1234.56 = 209.8752 rounds to 209.88
		finalDiscount = ChargeUtil.calculateDiscountAmount(rentalAgreement);
		assertEquals(finalDiscount, new BigDecimal("209.88"), "Final Discount for seventeen percent discount not 209.88");


		rentalAgreement.setDiscountPercent(13);
		// 13% of 1234.56 = 160.4928 rounds to 160.49
		finalDiscount = ChargeUtil.calculateDiscountAmount(rentalAgreement);
		assertEquals(finalDiscount, new BigDecimal("160.49"), "Final Discount for Thirteen percent discount not 106.49");

	}

}
