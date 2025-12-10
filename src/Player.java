import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected int chips;
    protected List<Card> holeCards = new ArrayList<>();
    protected boolean folded = false;

    public String getName() { return name; }

    public int getChips() { return chips; }

    public void addChips(int amount){ chips += amount; }

    public void deductChips(int amount){ chips -= amount; }

    public List<Card> getHoleCards(){ return holeCards; }

    public void setHoldCards(Card c){
        holeCards.add(c);
    }

    public boolean isFolded(){ return folded; }

    public void fold(){ folded = true; }

    abstract void bet(int amount);

    abstract void call(int amount);

    abstract void check();

    abstract void allIn();

    abstract Action takeAction(int currentBet, int pot, List<Card> communityCards);
}
