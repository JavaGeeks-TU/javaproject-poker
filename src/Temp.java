import java.util.ArrayList;
import java.util.List;

public abstract class Temp {
    protected String name;
    protected int chips;
    protected List<Card> holdCards = new ArrayList<>();
    protected boolean folded = false;

    public Temp(String name, int chips) {
        this.chips = chips;
        this.name = name;
    }

    public String getName() { return name; }

    public int getChips() { return chips; }

    public void addChips(int amount) { chips += amount; }

    public void deductChips(int amount) { chips -= amount; }

    public List<Card> getHoldCards() { return holdCards; }

    public void setHoldCards(Card c) {
        holdCards.add(c);
    }

    public boolean isFolded() { return folded; }

    public void fold() { folded = true; }

    abstract public int takeAction(int currentBet, int pot, List<Card> communityCards);

    abstract public int allIn();

    abstract public int check();

    abstract public int call(int amount);

    abstract public int bet(int amount);
}
