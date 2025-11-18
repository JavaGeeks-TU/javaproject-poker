import java.util.*;

public class Deck {
    ArrayList<Card> deck;

    public Deck() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card newCard = new Card(rank, suit);
                deck.add(newCard);
            }
        }
        Collections.shuffle(deck);
    }
}