package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/*
 * Game flow:
 * 1) Read player count and names.
 * 2) Create + shuffle deck; deal 2 cards to each player.
 * 3) For each player, run a Hit/Stay turn until they Stay or Bust.
 * 4) Show final hands and declare winner(s) (≤ 21 highest).
 */
public class Game {

    public void run() {
        try (Scanner in = new Scanner(System.in)) {

            // 1) Number of players (validated)
            System.out.print("Number of players: ");
            int n;
            while (true) {
                String s = in.nextLine().trim();
                try {
                    n = Integer.parseInt(s);
                    if (n > 0) break;
                } catch (NumberFormatException ignored) {}
                System.out.print("Enter a positive number: ");
            }

            // 2) Player names (non-empty)
            List<Player> players = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                System.out.print("Player " + i + " name: ");
                String name = in.nextLine().trim();
                if (name.isEmpty()) { i--; continue; }
                players.add(new Player(name));
            }

            // 3) Deck + shuffle
            Deck deck = new Deck();
            deck.shuffle();

            // Initial deal: two cards each (face-up)
            for (int round = 0; round < 2; round++) {
                for (Player p : players) {
                    Card c = deck.deal();
                    if (c != null) p.getHand().deal(c);
                }
            }

            // 4) Player turns: Hit / Stay
            for (Player p : players) {
                System.out.println("\n--- " + p.getName() + "'s turn ---");
                turnLoop(in, deck, p);
            }

            // 5) Final hands & scores
            System.out.println("\nFinal Hands & Scores");
            for (Player p : players) {
                Hand h = p.getHand();
                System.out.println(p.getName() + " -> " + h.show() + "  (" + h.getValue() + ")");
            }

            // 6) Determine winners (best ≤ 21)
            int best = -1;
            ArrayList<Player> winners = new ArrayList<>();
            for (Player p : players) {
                int s = p.getHand().getValue();
                if (s <= 21) {
                    if (s > best) {
                        best = s;
                        winners.clear();
                        winners.add(p);
                    } else if (s == best) {
                        winners.add(p);
                    }
                }
            }

            if (winners.isEmpty()) {
                System.out.println("\nEveryone busted.");
            } else if (winners.size() == 1) {
                System.out.println("\nWinner: " + winners.get(0).getName());
            } else {
                System.out.print("\nPush (Tie) between: ");
                for (int i = 0; i < winners.size(); i++) {
                    if (i > 0) System.out.print(", ");
                    System.out.print(winners.get(i).getName());
                }
                System.out.println();
            }
        }
    }

    // Single-player Hit/Stay turn. Ends on Stay, Bust, or natural 21.
    private void turnLoop(Scanner in, Deck deck, Player p) {
        Hand hand = p.getHand();

        while (true) {
            int value = hand.getValue();
            System.out.println(p.getName() + " -> " + hand.show() + "  (" + value + ")");

            if (value == 21) {
                System.out.println("Blackjack or 21! You stand automatically.");
                return;
            }
            if (value > 21) {
                System.out.println("Busted!");
                return;
            }

            System.out.print("(H)it or (S)tay? ");
            String cmd = in.nextLine().trim().toLowerCase();

            if (cmd.startsWith("h")) {
                Card c = deck.deal();
                if (c == null) {
                    System.out.println("Deck is empty. You must stay.");
                    return;
                }
                hand.deal(c);
            } else if (cmd.startsWith("s")) {
                System.out.println("Staying with " + value + ".");
                return;
            } else {
                // invalid input, prompt again
                System.out.println("Please type H to Hit or S to Stay.");
            }
        }
    }
}
