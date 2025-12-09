import java.util.*;

public class AIPlayer implements Player{
    private String name = "Computer";
    private int chips;
    private List<Card> holeCards = new ArrayList<>();
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
    public List<Card> getHoleCards() { return holeCards; }

    @Override
    public void setHoldCards(Card c) {
        holeCards.add(c);
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
    public int check() {
        System.out.println("Computer가 Check 했습니다.");
        return 0;
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
        holeCards.sort(Comparator.comparingInt(card->card.getRank().getValue()));
        if(holeCards.get(0).getRank().getValue() == holeCards.get(1).getRank().getValue()) return true; //페어인지
        if(holeCards.get(0).getSuit() == holeCards.get(1).getSuit()) return true;  //플러시
        if(holeCards.get(0).getRank().getValue()+1 == holeCards.get(1).getRank().getValue()) return true; //스트레이트

        //스트레이트 혹은 플래시 인지


        if (holeCards.get(0).getRank().getValue() >= 11 || holeCards.get(1).getRank().getValue() >= 11) return true; // A,K,Q,J

        return false;
    }

    //포스트플랍 족보 평가
    private int evaluateHand(List<Card> community) {
        // 원페어=1, 투페어=2, 트리플=3 ... 같은 형태로 점수화 (단순 버전)
        Rules r = new Rules();
        HandRank rank = r.hankRank(holeCards, community);
        return rank.handRanking().getScore();
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
