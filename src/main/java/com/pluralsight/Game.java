package com.pluralsight;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Game {

    public void run() {
        // Scanner is auto-closed via try-with-resources.
        try (Scanner scan = new Scanner(System.in)) {

            // 1) Prompt for number of players with simple numeric validation
            System.out.print("Number of players: ");
            int n;
            while (true) {
                String s = scan.nextLine().trim();
                try {
                    n = Integer.parseInt(s);
                    if (n > 0) break;                                // accept only positive counts
                } catch (NumberFormatException ignored) {}
                System.out.print("Enter a positive number: ");
            }

            // 2) Build the player list, re-asking on empty names
            List<Player> players = new ArrayList<>();
            for (int i = 1; i <= n; i++) {
                System.out.print("Player " + i + " Player name: ");
                String name = scan.nextLine().trim();
                if (name.isEmpty()) { i--; continue; }               // stay on same index if blank
                players.add(new Player(name));
            }

            // 3) Create and shuffle a standard 52-card deck
            Deck deck = new Deck();
            deck.shuffle();

            // 4) Initial deal: two face-up cards to each player
            for (int round = 0; round < 2; round++) {
                for (Player p : players) {
                    Card c = deck.deal();                           // returns the top card; flips it face-up
                    if (c != null) p.getHand().deal(c);
                }
            }

            // 5) Show hands and computed scores (Hand.show + Hand.getValue)
            System.out.println("\n Hands & Scores");
            for (Player p : players) {
                Hand h = p.getHand();
                System.out.println(p.getName() + " -> " + h.show() + "  (" + h.getValue() + ")");
            }

            // 6) Determine winners (best value ≤ 21). Ties produce a "Push".
            int best = -1;
            ArrayList<Player> winners = new ArrayList<>();
            for (Player p : players) {
                int score = p.getHand().getValue();
                if (score <= 21) {
                    if (score > best) {
                        best = score;
                        winners.clear();
                        winners.add(p);
                    } else if (score == best) {
                        winners.add(p);
                    }
                }
            }

            // 7) Display outcome: everyone busts, single winner, or a tie
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
}
