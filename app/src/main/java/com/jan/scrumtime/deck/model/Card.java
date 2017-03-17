package com.jan.scrumtime.deck.model;

/**
 * TODO: write class description
 */

public class Card {

    public Complexity complexity;

    public Card(Complexity complexity)
    {
        this.complexity = complexity;
    }

    public String getDisplayValue()
    {
        return complexity.getValue();
    }

    public int getThemeColour()
    {
        return complexity.getColourRes();
    }
}
