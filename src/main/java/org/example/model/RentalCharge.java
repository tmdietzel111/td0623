package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
 * Basic data for the Rental Charge - no fancy logic here just a POJO representing what should probably be a table
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCharge {
	private ToolType toolType;
	private BigDecimal dailyRate;
	private boolean weekdayCharge;
	private boolean weekendCharge;
	private boolean holidayCharge;
}
