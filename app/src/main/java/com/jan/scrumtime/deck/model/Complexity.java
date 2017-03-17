package com.jan.scrumtime.deck.model;

import java.text.DecimalFormatSymbols;

/**
 * TODO: write class description
 */

public enum Complexity {

    ZERO("0", 0), ONE("1", 0), TWO("2", 0), THREE("3", 0), FIVE("5", 0), EIGHT("8", 0), WHOKNOWS(
        "?", 0), IMPOSSIBLE(DecimalFormatSymbols.getInstance().getInfinity(), 0), BEER("BEER", 0);

    private String value;
    private int colourRes;

    Complexity(String value, int colourRes) {
        this.value = value;
        this.colourRes = colourRes;
    }

    public String getValue() {
        return value;
    }

    public int getColourRes() {
        return colourRes;
    }
}
