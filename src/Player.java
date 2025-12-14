import java.util.ArrayList;
import java.util.List;

public abstract class Player {
    protected String name;
    protected int chips;
    protected List<Card> holdCards = new ArrayList<>();
    protected boolean folded = false;
    protected boolean allined = false;
    protected HandRank handRank;

    public void winner(int pot){chips+=pot;};

    public void setHandRank(HandRank handRank){
        this.handRank = handRank;
    }

    public HandRank getHandRank(){return handRank;}

    public String getName() { return name; }

    public int getChips() { return chips; }

    public void addChips(int amount){ chips += amount; }

    public void allinChips(){ chips =0; }

    public void deductChips(int amount){ chips -= amount; }

    public List<Card> getHoldCards(){ return holdCards; }

    public void setHoldCards(Card c){
        holdCards.add(c);
    }

    public boolean isFolded(){ return folded; }

    public void fold(){ folded = true; }

    public void newgame(){
        this.folded = false;
        this.holdCards.clear();
    }

    abstract void raise(int amount);

    abstract void bet(int amount);

    abstract void call(int amount);

    abstract void check();

    abstract void allIn();

    abstract Action takeAction(int currentBet, int pot, List<Card> communityCards);
}
