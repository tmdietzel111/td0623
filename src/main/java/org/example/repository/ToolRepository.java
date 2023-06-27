package org.example.repository;

import org.example.exception.CheckoutException;
import org.example.model.RentalCharge;
import org.example.model.Tool;
import org.example.model.ToolType;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.HashMap;

/**
 * Mock Repository representing static data - for the purposes of this demo it is purely a static hashmap
 */
@Repository
public class ToolRepository {
	private final HashMap<String, Tool> tools;
	private final HashMap<ToolType, RentalCharge> rentalCharges;

	/**
	 * Basic constructor - no connections or anything to hookup - just sets up the data
	 */
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

	/**
	 * Returns the tool information for the passed in Code
	 * <p>
	 * If the tool does not exist, it throws a CheckoutException as there's not really much to do in this case
	 *
	 * @param code
	 * @return populated Tool information
	 */
	public Tool getToolByCode(String code) {
		if (tools.containsKey(code)) {
			return tools.get(code);
		}
		throw new CheckoutException(String.format("Tool Code %s not found", code));
	}

	/**
	 * Returns the rental charge for the given toolType
	 * <p>
	 * ToolType is an enum, so this should always return something, but if it cannot find the ToolType it will throw a checkout exception
	 *
	 * @param toolType
	 * @return
	 */
	public RentalCharge getRentalCharges(ToolType toolType) {
		if (rentalCharges.containsKey(toolType)) {
			return rentalCharges.get(toolType);
		}
		//Really hard to throw this right now, but in principle lets leave it
		//Maybe this should be a 500 since tool Type is internal? How much are we exposing?
		throw new CheckoutException(String.format("Tool Type not Found", toolType));
	}

}
