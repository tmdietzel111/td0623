package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.time.LocalDate;

/**
 * Simple Data object for the POST body we expect on the API - simple JSON formatters for dates, all fields are expected
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

	@NotNull(message = "Must Have a Tool Code")
	private String toolCode;

	@NotNull(message = "Need a count of Rental Days")
	@Min(value = 0, message = "Must rent for at least one Day")
	private Integer rentalDays;

	@NotNull(message = "Must have a checkout date")
	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate checkoutDate;

	@NotNull(message = "Need a Discount Per")
	@Min(value = 0, message = "Discount Percent must be at least 0")
	@Max(value = 100, message = "Discount Cannot be more than 100")
	private Integer discountPercent;

}
