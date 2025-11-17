public enum Suit {
    Spade('♠', 4),
    Club('♣',1),
    Diamond('◆', 3),
    Heart('♥',2);

    private final char value;
    private final int score;
    private Suit(char value, int score) {
        this.value = value;
        this.score = score;
    }
    public char getValue() {
        return value;
    }
    public int getScore() {
        return score;
    }
}
