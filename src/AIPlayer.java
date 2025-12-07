import java.util.*;

public class AIPlayer implements Player{
    private String name = "Computer";
    private int chips;
    private Card[] holeCards = new Card[2];
    private boolean folded = false;

    public AIPlayer(int chips) {
        this.chips = chips;
    }

    @Override
    public String getName() { return name; }

    @Override
    public int getChips() { return chips; }

    @Override
    public void addChips(int amount) { chips += amount; }

    @Override
    public void deductChips(int amount) { chips -= amount; }

    @Override
    public Card[] getHoleCards() { return holeCards; }

    @Override
    public void setHoleCards(Card c1, Card c2) {
        holeCards[0] = c1;
        holeCards[1] = c2;
    }

    @Override
    public boolean isFolded() { return folded; }

    @Override
    public void fold() { folded = true; }

    @Override
    public int bet(int amount) {
        amount = Math.min(amount, chips);
        deductChips(amount);
        System.out.println("Computer가 " + amount + "을 Bet/Raise 했습니다.");
        return amount;
    }

    @Override
    public int call(int amount) {
        int callAmount = Math.min(amount, chips);
        deductChips(callAmount);
        System.out.println("Computer가 " + callAmount + "을 Call 했습니다.");
        return callAmount;
    }

    @Override
    public void check() {
        System.out.println("Computer가 Check 했습니다.");
    }

    @Override
    public int allIn() {
        int amount = chips;
        chips = 0;
        System.out.println("Computer가 All-in! ( " + amount + " )");
        return amount;
    }

    //프리플랍 평가 함수
    private boolean isStrongPreflopHand() {
        Card a = holeCards[0];
        Card b = holeCards[1];

        boolean isPair = a.getRank() == b.getRank();
        boolean suited = a.getSuit() == b.getSuit();
        int diff = Math.abs(a.getRank() - b.getRank());

        // 포켓페어, 높은 카드, 수딧 커넥터
        if (isPair) return true;
        if (a.getRank() >= 11 && b.getRank() >= 11) return true; // A,K,Q,J
        if (suited && diff == 1) return true;                     // 8-9 suited 등

        return false;
    }

    //포스트플랍 족보 평가
    private int evaluateHand(List<Card> community) {
        // 원페어=1, 투페어=2, 트리플=3 ... 같은 형태로 점수화 (단순 버전)
        return HandEvaluator.evaluate(holeCards, community);
    }

    @Override
    public int takeAction(int currentBet, int pot, List<Card> communityCards) {

        try { Thread.sleep(900); } catch (Exception e) {}

        boolean preflop = communityCards.size() == 0;

        if (preflop) {
            return decidePreflop(currentBet);
        } else {
            return decidePostFlop(currentBet, communityCards);
        }
    }

    //프리플랍 액션
    private int decidePreflop(int currentBet) {

        boolean strong = isStrongPreflopHand();

        if (strong) {
            // 강한 핸드
            if (currentBet == 0) {
                // 아무도 베팅 안 함 → Bet (or Raise)
                return bet(30);
            } else {
                // 상대 Raise 있음 → Call
                return call(currentBet);
            }
        } else {
            // 약한 핸드
            if (currentBet == 0) {
                return check();
            } else {
                // 상대가 Bet 했으면 Fold
                fold();
                return -1;
            }
        }
    }

    //포스트플랍 액션
    private int decidePostFlop(int currentBet, List<Card> community) {

        int handRank = evaluateHand(community);

        if (handRank >= 3) {
            // Two Pair 이상 → 공격적
            if (currentBet == 0)  return bet(40);
            else return call(currentBet);
        }
        else if (handRank >= 1) {
            // One Pair ~ Two Pair 사이 → 보통 공격
            if (currentBet == 0) return check();
            else return call(currentBet);
        }
        else {
            // High card → 약함
            if (currentBet == 0) return check();
            else {
                fold();
                return -1;
            }
        }
    }
}
