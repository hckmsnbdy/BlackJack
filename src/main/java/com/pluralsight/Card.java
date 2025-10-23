package com.pluralsight;

public class Card {
    private final String suit;
    private final String value;
    private boolean isFaceUp;
    public Card(String suit, String value) {
        this.suit = suit;
        this.value = value;
        this.isFaceUp = false;                          // cards start face-down; the deck flips them when dealing
    }
    public String getSuit() {
        // Hide suit if the card is face-down
        if (isFaceUp) {
            return suit;
        } else {
            return "#";
        }
    }
    public String getValue() {
        // Hide rank if the card is face-down
        if (isFaceUp) {
            // this is the string value of the card (A, K, Q, J, 10, 9 ...)
            return value;
        } else {
            return "#";
        }
    }
    public int getPointValue() {
        // Only expose a value when visible. Otherwise, treat as 0.
        if (!isFaceUp) {
            return 0;
        }
        // determine point value and return it
        // A = 11, K/Q/J = 10, numeric = face value
        switch (value) {
            case "A": return 11;
            case "K":
            case "Q":
            case "J": return 10;
            default:  return Integer.parseInt(value); // "2".."10"
        }
    }
    public boolean isFaceUp() {
        return isFaceUp;
    }

    public void flip() {
        isFaceUp = !isFaceUp;                       // Toggle visibility (face-down -> face-up or vice versa)
    }

    @Override
    public String toString() {
        return isFaceUp ? (value + " of " + suit) : "##";
    }
}