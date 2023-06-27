package org.example.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Checkout {

	private String toolCode;
	private Integer rentalDays;

	@JsonFormat(pattern = "MM-dd-yyyy")
	private LocalDate checkoutDate;

	private Integer discountPercent;

}
