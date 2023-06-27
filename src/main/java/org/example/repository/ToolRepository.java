package org.example.repository;

import org.example.model.RentalCharge;
import org.example.model.Tool;
import org.example.model.ToolType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;

@Repository
public class ToolRepository {
	private final HashMap<String, Tool> tools;
	private final HashMap<ToolType, RentalCharge> rentalCharges;

	public ToolRepository() {
		//This should talk to a DB, but this is a sample so you get a privately initialized hashmap instead
		//But this is static data

		tools = new HashMap<>();
		Tool CHNS = new Tool("CHNS", ToolType.CHAINSAW, "Stihl");
		Tool LADW = new Tool("LADW", ToolType.LADDER, "Werner");
		Tool JAKD = new Tool("JAKD", ToolType.JACKHAMMER, "DeWalt");
		Tool JAKR = new Tool("JAKR", ToolType.JACKHAMMER, "Ridgid");

		tools.put(CHNS.getToolCode(), CHNS);
		tools.put(LADW.getToolCode(), LADW);
		tools.put(JAKD.getToolCode(), JAKD);
		tools.put(JAKR.getToolCode(), JAKR);

		rentalCharges = new HashMap<>();

		rentalCharges.put(ToolType.LADDER, new RentalCharge(ToolType.LADDER, new BigDecimal("1.99"), true, true, false));
		rentalCharges.put(ToolType.CHAINSAW, new RentalCharge(ToolType.CHAINSAW, new BigDecimal("1.49"), true, false, true));
		rentalCharges.put(ToolType.JACKHAMMER, new RentalCharge(ToolType.JACKHAMMER, new BigDecimal("2.99"), true, false, false));

	}

	public Tool getToolByCode(String code) {
		return tools.get(code);
	}

	public RentalCharge getRentalCharges(ToolType toolType) {
		return rentalCharges.get(toolType);
	}

}
