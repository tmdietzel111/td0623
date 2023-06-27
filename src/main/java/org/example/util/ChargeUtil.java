package org.example.util;

import org.example.model.RentalAgreement;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * Basic class to handle all the BigDecimal math and rounding
 * <p>
 * Allows for a single place to update scale if we need to change it later and if the charges
 * get more complicated and keeps the service's logic simple
 */
public class ChargeUtil {
	//Need 100 to set percentages
	private static final BigDecimal ONE_HUNDRED = new BigDecimal(100);

	/**
	 * Simple calculation - Daily Rental Charge * Charge days. Round to 2 decimal places up by half
	 * <p>
	 * Not null checked - no realistic way for both values to be null
	 *
	 * @param rentalAgreement
	 * @return BigDecimal, rounded to two values of the pre-discount charge
	 */
	public static BigDecimal calculatePreDiscountCharge(RentalAgreement rentalAgreement) {

		return rentalAgreement.getDailyRentalCharge().multiply(
				new BigDecimal(rentalAgreement.getChargeDays())).setScale(2, RoundingMode.HALF_UP);

	}

	/**
	 * Calculate the discount amount - if preDiscount amount is not pre-calculated this will probably be negative
	 * <p>
	 * Math is to take the *integer* discount amount, multiple it by the preDiscountCharge then divide by 100 to set the percent
	 *
	 * @param rentalAgreement
	 * @return
	 */
	public static BigDecimal calculateDiscountAmount(RentalAgreement rentalAgreement) {
		BigDecimal preDiscountCharge = rentalAgreement.getPreDiscountCharge();

		BigDecimal discountPercent = new BigDecimal(rentalAgreement.getDiscountPercent());
		return preDiscountCharge.multiply(discountPercent).divide(ONE_HUNDRED).setScale(2, RoundingMode.HALF_UP);
	}


}
