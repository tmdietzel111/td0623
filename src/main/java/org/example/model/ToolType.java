package org.example.model;

/**
 * Enum, all currently known tool types.
 * <p>
 * There are no more tools in the *ENTIRE* world, just these three
 * <p>
 * Until there's more later - be sure to update the table
 */
public enum ToolType {
	CHAINSAW("Chainsaw"),
	LADDER("Ladder"),
	JACKHAMMER("JackHammer");

	private final String name;

	ToolType(String name) {
		this.name = name;
	}
}
