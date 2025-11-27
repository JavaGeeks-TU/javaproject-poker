import java.util.List;

public interface Player {

    String getName();
    int getChips();
    void addChips(int amount);
    void deductChips(int amount);

    Card[] getHoleCards();
    void setHoldCards(Card c1, Card c2);

    boolean isFolded();
    void fold();

    //액션 메서드
    int bet(int amount);  //Bet or Raise
    int call(int amount); //Call
    void check();         //Check
    int allin();          //All in

    //현재 턴에 어떤 액션을 할지 결정
    int takeAction(int currentBet, int pot, List<Card> communityCards);
}
