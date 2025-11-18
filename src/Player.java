import java.util.*;

public class Player {
    private String name;          //플레이어 이름
    private int chips;            //현재 칩 보유량
    private boolean folded;       //이번 라운드에서 폴드했는가?
    private int currentBet;       //현재 라운드에서 베팅한 금액
    private List<Card> hand;      //홀 카드 (2장)

    public Player(String name, int initialChips) {
        this.name = name;
        this.chips = initialChips;
        this.folded = false;
        this.currentBet = 0;
        this.hand = new ArrayList<>();
    }

    /*플레이어가 카드를 받는다 (2장 보유 */
    public void receiveCard(Card card) {
        if (hand.size() < 2) {
            hand.add(card);
        }
    }

    /*핸드 초기화 (다음 라운드 위해)*/
    public void resetHand() {
        hand.clear();
    }

    /*현재 핸드 가져오기*/
    public List<Card> getHand() {
        return hand;
    }

    //베팅 관련 기능
    /*베팅(칩 소모)*/
    public int bet(int amount) {
        if (amount >= chips) {
            //가진 칩보다 크게 베팅 -> 올인(All-in)
            int allInAmount = chips;
            chips = 0;
            currentBet += allInAmount;
            return allInAmount;
        }
        chips -= amount;
        currentBet += amount;
        return amount;
    }

    /*콜(Call)*/
    public int call(int targetBet) {
        int needed = targetBet - currentBet;
        return bet(needed);
    }

    /*라운드 베팅액 리셋*/
    public void resetBet() {
        currentBet = 0;
    }

    /*폴드(Fold)*/
    public void fold() {
        folded = true;
    }

    //상태/정보 조회
    public boolean isFolded() {
        return folded;
    }

    public String getName() {
        return name;
    }

    public int getChips() {
        return chips;
    }

    public int getCurrentBet() {
        return currentBet;
    }

    //턴 초기화
    public void startNewRound() {
        folded = false;
        currentBet = 0;
        hand.clear();
    }

    @Override
    public String toString(){
        return "[" + name + "] Chips: " + chips +
                ", Bet: " + currentBet +
                ", Folded: " + folded;
    }
}
