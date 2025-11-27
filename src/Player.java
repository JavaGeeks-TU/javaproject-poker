import java.util.List;

public interface Player {

    String getName();
    int getChips();
    int getCurrentBet();
    List<Card> getHand();

    void receiveCard(Card card);
    void resetHand();
    void bet(int amount);
    void call(int highestBet);
    void fold();
    boolean isFolded();

    String takeAction(List<Card> cards communityCards, int currentBet, int pot);
}
