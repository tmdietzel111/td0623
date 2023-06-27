package org.example.service;

import org.example.model.*;
import org.example.repository.ToolRepository;
import org.example.util.DayCountingUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class CheckoutService {
	private final ToolRepository toolRepository;

	@Autowired
	public CheckoutService(ToolRepository toolRepository) {
		this.toolRepository = toolRepository;
	}

	public Tool getTool(String code) {
		return toolRepository.getToolByCode(code);
	}

	public RentalCharge getRentalCharge(ToolType toolType) {
		return toolRepository.getRentalCharges(toolType);
	}

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

		calculateCharges(rentalAgreement);

		return rentalAgreement;
	}


	private RentalAgreement calculateCharges(RentalAgreement rentalAgreement) {
		BigDecimal preDiscountCharge = rentalAgreement.getDailyRentalCharge().multiply(
				new BigDecimal(rentalAgreement.getChargeDays()));
		rentalAgreement.setPreDiscountCharge(preDiscountCharge);

		//TODO disgusting fix me
		BigDecimal discountPercent = new BigDecimal(rentalAgreement.getDiscountPercent());
		BigDecimal oneHundred = new BigDecimal(100);
		BigDecimal discountAmount = preDiscountCharge.multiply(discountPercent).divide(oneHundred);
		rentalAgreement.setDiscountAmount(discountAmount);

		rentalAgreement.setFinalCharge(rentalAgreement.getPreDiscountCharge().subtract(rentalAgreement.getDiscountAmount()));
		return rentalAgreement;
	}


}
