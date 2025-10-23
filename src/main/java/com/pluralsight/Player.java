package com.pluralsight;

public class Player {
    private final String name;
    private final Hand hand;

    public Player(String name) {
        if (name == null || name.isBlank()) throw new IllegalArgumentException("name");
        this.name = name.trim();
        this.hand = new Hand();
    }

    public String getName() { return name; }
    public Hand getHand()   { return hand; }
}
