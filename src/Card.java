public class Card {
    //Rank 및 Suit 가져오기
    private final Rank rank;
    private final Suit suit;

    //생성자
    public Card(Rank rank, Suit suit) {
        this.rank = rank;
        this.suit = suit;
    }
    //rank와 suit 값 가져오기
    public Rank getRank() {
        return rank;
    }
    public Suit getSuit() {
        return suit;
    }

    //깔끔하게 출력 해주기
    // 오버라이드 붙이던지~
    @Override
    public String toString() {
        return suit.getSuit()+rank.getName();
    }


}
