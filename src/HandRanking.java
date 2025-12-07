public enum HandRanking {
    HighCard(1,"HighCard"),
    OnePair(2,"OnePair"),
    TwoPair(3,"TwoPair"),
    Triple(4,"Triple"),
    Straight(5,"Straight"),
    Flush(6,"Flush"),
    FullHouse(7,"FullHouse"),
    FourCard(8,"FourCard"),
    StraightPlush(9,"StraightPlush"),
    RoyalStraightPlush(10,"RoyalStraightPlush");

    private final int score;
    private final String name;
    HandRanking(int value, String name) {
        this.score = value;
        this.name = name;
    }
    public int getScore() {
        return score;
    }
    public String getName() {
        return name;
    }

}
