package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Response object for the Checkout API - contains all the fields we need
 * <p>
 * Note, some of these could be considered derived fields (i.e. finalCharge, preDiscountCharge) but
 * as this feels like the kind of thing that could be cached in a database, we declare them individually
 */
@Data
public class RentalAgreement {

	private String toolCode;
	private ToolType toolType;
	private String toolBrand;
	private Integer rentalDays;
	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate checkoutDate;

	@JsonFormat(pattern = "MM/dd/yyyy")
	private LocalDate dueDate;
	private BigDecimal dailyRentalCharge;
	private Integer chargeDays;
	private BigDecimal preDiscountCharge;
	private Integer discountPercent;
	private BigDecimal discountAmount;
	private BigDecimal finalCharge;


	/**
	 * Build the basic rental agreement based on the Tool, Rental Charge and Checkout passed in -
	 * sets all basic derived fields - including setting chargedDays to rentalDays
	 *
	 * @param tool         Tool used in the Agreement
	 * @param rentalCharge Rental Charge associated with the tool
	 * @param checkout     Checkout details about the agreement
	 */
	public RentalAgreement(Tool tool, RentalCharge rentalCharge, Checkout checkout) {
		toolCode = checkout.getToolCode();
		toolType = tool.getToolType();
		toolBrand = tool.getBrand();
		rentalDays = checkout.getRentalDays();
		//Starting charge days at the same as rentalDays, we'll remove uncharged days
		chargeDays = rentalDays;
		checkoutDate = checkout.getCheckoutDate();

		//dueDate is derived from the above two fields
		//Checked out on Friday means due on Saturday and we only charge for Friday
		//TODO verify above assumption
		dueDate = checkoutDate.plusDays(rentalDays);

		dailyRentalCharge = rentalCharge.getDailyRate();
		discountPercent = checkout.getDiscountPercent();

		//Prevent null pointers

		preDiscountCharge = new BigDecimal(0);
		discountAmount = new BigDecimal(0);
		finalCharge = new BigDecimal(0);
	}


	/**
	 * removes the passed in number of charge days simply for convenience
	 *
	 * @param daysToRemove How many days to remove from the charge day
	 * @return the new charge day value
	 */
	public Integer removeChargeDays(int daysToRemove) {
		return chargeDays -= daysToRemove;

	}

	/**
	 * Spits out a description of this rental agreement to the console/log
	 * <p>
	 * TODO Be less hardcoded? also maybe write to a log rather than console
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

	/**
	 * Simple method, in case we want to do logging differently in the future, right now just a print statement
	 *
	 * @param message The message to 'log' or send to console
	 */
	private void logMessage(String message) {
		//TODO Setup Logging
		System.out.println(message);
	}
}
