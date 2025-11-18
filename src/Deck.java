import java.util.*;

public class Deck {
    //arraylist 덱 생성
    ArrayList<Card> deck;

    //생성자
    public Deck() {
        deck = new ArrayList<>();
        for (Suit suit : Suit.values()) {
            for (Rank rank : Rank.values()) {
                Card newCard = new Card(rank, suit);
                deck.add(newCard);
            }
        }
        //섞기
        Collections.shuffle(deck);
    }
}