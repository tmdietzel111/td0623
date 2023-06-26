package org.example.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RentalCharge {
    private ToolType toolType;
    private BigDecimal dailyRate;
    private Boolean weekdayChange;
    private Boolean weekendChange;
    private Boolean holidayCharge;
}