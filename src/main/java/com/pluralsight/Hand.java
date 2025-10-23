package com.pluralsight;

import java.util.ArrayList;

public class Hand {
    private final ArrayList<Card> cards;

    public Hand() {
        cards = new ArrayList<>();
    }

    // Standardized adder
    public void deal(Card card) {
        cards.add(card);
    }

    public int getSize() {
        return cards.size();
    }

    public int getValue() {
        int total = 0;
        int aceCount = 0;

        for (Card card : cards) {
            boolean wasFaceUp = card.isFaceUp();
            if (!wasFaceUp) card.flip();              // temporarily expose

            total += card.getPointValue();            // A reports 11 here
            String rank = card.getValue();            // visible rank string
            if ("A".equals(rank)) aceCount++;

            if (!wasFaceUp) card.flip();              // restore original visibility
        }

        // Downgrade Aces (11->1) as needed to avoid bust
        while (total > 21 && aceCount > 0) {
            total -= 10;  // make one Ace worth 1 instead of 11
            aceCount--;
        }
        return total;
    }

    public String show() {
        StringBuilder sb = new StringBuilder();
        for (Card c : cards) sb.append(c.toString()).append(" ");
        return sb.toString().trim();
    }
}
