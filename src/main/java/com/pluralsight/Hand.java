package com.pluralsight;

import java.util.ArrayList;

public class Hand {
    private ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    public void deal(Card card) {
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }
    // The Hand uses the methods of each card to determine the value of each card - and adds up all values
    public int getValue() {
        int value = 0;
        for (Card card : cards) {
            boolean wasFaceUp = card.isFaceUp();
            if (!wasFaceUp) card.flip();        // turn the card over to see the value
            value += card.getPointValue();
            if (!wasFaceUp) card.flip();        // hide the card again
        }
        return value;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        for (Card c : cards) {
            sb.append(c.toString()).append(" ");
        }
        return sb.toString().trim();
    }
}
