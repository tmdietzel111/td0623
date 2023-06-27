package org.example.model;

public enum ToolType {
	CHAINSAW("Chainsaw"),
	LADDER("Ladder"),
	JACKHAMMER("JackHammer");

	private final String name;

	ToolType(String name) {
		this.name = name;
	}
}
