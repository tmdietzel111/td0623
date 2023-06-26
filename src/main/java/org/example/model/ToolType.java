package org.example.model;

public enum ToolType {
    CHAINSAW("Chainsaw"),
    LADDER("Ladder"),
    JACKHAMMER ("JackHammer");

    private String name;

    private ToolType (String name) {
        this.name = name;
    }
}
