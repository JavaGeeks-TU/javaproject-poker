public enum Suit {
    //문양, 값
    Spade('♠', 4),
    Club('♣',1),
    Diamond('◆', 3),
    Heart('♥',2);

    private final char suit;
    private final int score;
    private Suit(char value, int score) {
        this.suit = value;
        this.score = score;
    }
    public char getSuit() {
        return suit;
    }
    public int getScore() {
        return score;
    }
}