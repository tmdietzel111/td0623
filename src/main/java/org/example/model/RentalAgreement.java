package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RentalAgreement {

	private String toolCode;
	private ToolType toolType;
	private String toolBrand;
	private Integer rentalDays;
	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate checkoutDate;

	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate dueDate;
	private BigDecimal dailyRentalCharge;
	private Integer chargeDays;
	private BigDecimal preDiscountCharge;
	private Integer discountPercent;
	private BigDecimal discountAmount;
	private BigDecimal finalCharge;


	/**
	 * Build the basic rental agreement based on the Tool, Rental Charge and Checkout passed in
	 *
	 * @param tool
	 * @param rentalCharge
	 * @param checkout
	 */
	public RentalAgreement(Tool tool, RentalCharge rentalCharge,
						   Checkout checkout) {
		toolCode = checkout.getToolCode();
		toolType = tool.getToolType();
		toolBrand = tool.getBrand();
		rentalDays = checkout.getRentalDays();
		//Starting charge days at the same as rentalDays, we'll remove uncharged days
		chargeDays = rentalDays;
		checkoutDate = checkout.getCheckoutDate();

		//dueDate is derived from the above two fields
		//Note, I'm assuming a One day rental is due the same day its checked out
		//TODO Verify this assumption
		dueDate = checkoutDate.plusDays(rentalDays - 1);

		dailyRentalCharge = rentalCharge.getDailyRate();
		discountPercent = checkout.getDiscountPercent();
	}

	public void setDiscountAmount(BigDecimal discountAmount) {
		if (discountPercent != null) {
			this.discountAmount = discountAmount.setScale(2, RoundingMode.HALF_UP);
		}
	}

	/**
	 * removes the passed in number of charge days
	 *
	 * @param daysToRemove
	 * @return
	 */
	public Integer removeChargeDays(int daysToRemove) {
		return chargeDays -= daysToRemove;

	}

	/**
	 * Spits out a description of this rental agreement to the console/log
	 * <p>
	 * TODO Be less hardcoded
	 */
	public void toConsole() {
		logMessage("Tool Code : " + toolCode);
		logMessage("Tool type : " + toolType.name());
		logMessage("Tool Brand : " + toolBrand);
		logMessage("Rental Days : " + rentalDays);
		logMessage("Check out date  : " + checkoutDate.toString());
		logMessage("Due Date : " + dueDate.toString());
		logMessage("Daily Rental Charge : $" + dailyRentalCharge);
		logMessage("Charge Days : " + chargeDays);
		logMessage("Pre-discount charge : " + preDiscountCharge);
		logMessage("Discount Percent : " + discountPercent + "%");
		logMessage("Discount amount : $" + discountAmount);
		logMessage("Final Charge : $" + finalCharge);


	}

	private void logMessage(String message) {
		//TODO Setup Logging
		System.out.println(message);
	}
}
