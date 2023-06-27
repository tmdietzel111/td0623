package org.example.service;

import org.example.model.*;
import org.example.repository.ToolRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.DayOfWeek;
import java.time.LocalDate;

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


		//Arguably, we should put a check for weekday here, but all three tools charge for weekdays, lets move on

		//If we don't charge for weekends, remove them
		if (Boolean.FALSE.equals(rentalCharge.getWeekendCharge())) {
			removeWeekends(rentalAgreement);
		}

		//If we don't charge for holidays, remove those too
		if (Boolean.FALSE.equals(rentalCharge.getHolidayCharge())) {
			removeHolidays(rentalAgreement);
		}

		calculateCharges(rentalAgreement);

		return rentalAgreement;
	}


	private RentalAgreement removeWeekends(RentalAgreement rentalAgreement) {
		//could Clone, don't see a reason to

		LocalDate startDate = rentalAgreement.getCheckoutDate();
		//I should be able to do something with this...
		LocalDate endDate = rentalAgreement.getDueDate();

		int weekendDays = 0;
		//Really Really dumb way
		for (int i = 0; i < rentalAgreement.getRentalDays(); i++) {
			DayOfWeek dayOfWeek = startDate.plusDays(i).getDayOfWeek();
			if (DayOfWeek.SATURDAY.equals(dayOfWeek) || DayOfWeek.SUNDAY.equals(dayOfWeek)) {
				weekendDays++;
			}
		}

		rentalAgreement.removeChargeDays(weekendDays);
		return rentalAgreement;
	}


	private RentalAgreement removeHolidays(RentalAgreement rentalAgreement) {
		int holidays = 0;

		LocalDate startDate = rentalAgreement.getCheckoutDate();
		LocalDate endDate = rentalAgreement.getDueDate();

		int startYear = startDate.getYear();
		int endYear = endDate.getYear();

		for (int year = startYear; year <= endYear; year++) {
			LocalDate forthOfJuly = getForthOfJulyHoliday(year);
			LocalDate laborDay = getLaborDay(year);

			if ((forthOfJuly.isAfter(startDate) || forthOfJuly.isEqual(startDate))
					&& (forthOfJuly.isBefore(endDate) || forthOfJuly.isEqual(endDate))) {
				holidays++;
			}

			if ((laborDay.isAfter(startDate) || laborDay.isEqual(startDate))
					&& (laborDay.isBefore(endDate) || laborDay.isEqual(endDate))) {
				holidays++;
			}

		}

		rentalAgreement.removeChargeDays(holidays);
		return rentalAgreement;
	}

	private LocalDate getForthOfJulyHoliday(int year) {
		LocalDate forthOfJuly = LocalDate.of(year, 7, 4);
		//Dumb way to do it but functional
		if (forthOfJuly.getDayOfWeek().equals(DayOfWeek.SATURDAY)) {
			return forthOfJuly.minusDays(1);
		}
		if (forthOfJuly.getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
			return forthOfJuly.plusDays(1);
		}

		return forthOfJuly;
	}

	private LocalDate getLaborDay(int year) {
		LocalDate laborDay = LocalDate.of(year, 9, 1);

		//More dumb solutions
		while (!DayOfWeek.MONDAY.equals(laborDay.getDayOfWeek())) {
			laborDay = laborDay.plusDays(1);
		}

		return laborDay;
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
