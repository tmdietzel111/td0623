package org.example.service;

import org.example.model.*;
import org.example.repository.ToolRepository;
import org.example.util.ChargeUtil;
import org.example.util.DayCountingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Service Layer, handles all of the main logic for doing the checkout
 * <p>
 * Gets the data from the repository, walks through the business logic to calculate days etc
 */
@Service
public class CheckoutService {
	private final ToolRepository toolRepository;

	@Autowired
	public CheckoutService(ToolRepository toolRepository) {
		this.toolRepository = toolRepository;
	}

	/**
	 * Simple method to go get the tool from the repository
	 * <p>
	 * Note: For this example, this method is overkill - but it would make sense to extend this API to allow users to pull tools directly
	 * So I made it a standalone public method
	 *
	 * @param code
	 * @return
	 */
	public Tool getTool(String code) {
		return toolRepository.getToolByCode(code);
	}

	/**
	 * Simple method to go get the Rental Charge from the repository
	 * <p>
	 * Note: For this example, this method is overkill - but it would make sense to extend this API to allow users to pull Tool Charges directly
	 * So I made it a standalone public method
	 *
	 * @param toolType
	 * @return
	 */
	public RentalCharge getRentalCharge(ToolType toolType) {
		return toolRepository.getRentalCharges(toolType);
	}

	/**
	 * Calculates the rental agreement for the passed in checkout
	 * <p>
	 * Assumes the checkout is valid (Controller validation) so does not do much redundant checking
	 *
	 * @param checkout
	 * @return
	 */
	public RentalAgreement getRentalAgreement(Checkout checkout) {
		Tool basicTool = getTool(checkout.getToolCode());
		RentalCharge rentalCharge = getRentalCharge(basicTool.getToolType());
		RentalAgreement rentalAgreement = new RentalAgreement(basicTool, rentalCharge, checkout);


		if (!rentalCharge.isWeekdayCharge()) {
			//TODO implement once 'free on weekdays' is a use case
		}
		//If we don't charge for weekends, remove them
		if (!rentalCharge.isWeekendCharge()) {
			rentalAgreement.removeChargeDays(DayCountingUtil.countWeekends(rentalAgreement));
		}

		//If we don't charge for holidays, remove those too
		if (!rentalCharge.isHolidayCharge()) {
			rentalAgreement.removeChargeDays(DayCountingUtil.countHolidays(rentalAgreement));
		}

		rentalAgreement.setPreDiscountCharge(ChargeUtil.calculatePreDiscountCharge(rentalAgreement));
		rentalAgreement.setDiscountAmount(ChargeUtil.calculateDiscountAmount(rentalAgreement));

		//This is simple math, could be externalized but we're just doing it here since there isn't any rounding rules
		rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge().
				subtract(rentalAgreement.getDiscountAmount()));


		return rentalAgreement;
	}


}
