import java.util.*;

public class Deck {
    //arraylist 덱 생성
    // 좋은 습관 = ArrayList (X) / List (O) 
    // 최대한 추상적인 인터페이스를 사용하자
    private List<Card> deck;

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

    public Card drawCard() {
        if(deck.isEmpty())
            return null;
        return deck.remove(0);
    }
}