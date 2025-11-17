public enum Rank {
    Ace("A", 1,14),
    Two("2", 2),
    Three("3",3),
    Four("4",4),
    Five("5",5),
    Six("6",6),
    Seven("7",7),
    Eight("8",8),
    Nine("9",9),
    Ten("10",10),
    Jeck("J",11),
    Queen("Q",12),
    King("K", 13);

    private final String name;
    private final int value;
    private final int othervalue;
    private Rank(String name, int value) {
        this.name = name;
        this.value = value;
        this.othervalue = value;
    }

    private Rank(String name, int value,  int othervalue) {
        this.name = name;
        this.value = value;
        this.othervalue = othervalue;
    }

    public String getName() {
        return name;
    }
    public int getValue() {
        return value;
    }
    public int getOthervalue() {
        return othervalue;
    }
}
