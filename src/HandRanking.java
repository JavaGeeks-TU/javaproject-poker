public enum HandRanking {
    HighCard(0,"HighCard"),
    OnePair(1,"OnePair"),
    TwoPair(2,"TwoPair"),
    Triple(3,"Triple"),
    Straight(4,"Straight"),
    Flush(5,"Flush"),
    FullHouse(6,"FullHouse"),
    FourCard(7,"FourCard"),
    StraightPlush(8,"StraightPlush"),
    RoyalStraightPlush(9,"RoyalStraightPlush");

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
