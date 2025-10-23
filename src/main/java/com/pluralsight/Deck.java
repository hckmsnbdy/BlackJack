package com.pluralsight;

import java.util.ArrayList;
import java.util.Collections;

public class Deck {
    private final ArrayList<Card> cards;

    public Deck() {
        cards = new ArrayList<>();
        String[] suits = {"Hearts", "Spades", "Diamonds", "Clubs"};
        String[] values = {"2","3","4","5","6","7","8","9","10","J","Q","K","A"};

        // Populate the deck: for each suit and rank, create a Card and append it
        for (String suit : suits) {
            for (String value : values) {
                Card card = new Card(suit, value);
                cards.add(card);
            }
        }
    }
    public void shuffle(){
        // Randomize card order; for testability, consider a seeded Random overload.
        Collections.shuffle(cards);
    }
    public Card deal() {
        // Deal the top card (if any), then flip it face-up so the UI and scoring can see it.
        if (cards.size() > 0) {
            Card card = cards.remove(0);
            card.flip();
            return card;
        } else {
            return null;
        }
    }
    public int getSize(){
        return cards.size();
    }
}
