import java.util.*;

public class AIPlayer extends Player {
    private static int num = 1;

    public AIPlayer(int chips) {
        this.name = "Computer " + num++;
        this.chips = chips;
    }

    @Override
    public void bet(int amount) {
        amount = Math.min(amount, chips);
        deductChips(amount);
        System.out.println("Computer가 " + amount + "을 Bet/Raise 했습니다.");
    }

    @Override
    public void call(int amount) {
        int callAmount = Math.min(amount, chips);
        deductChips(callAmount);
        System.out.println("Computer가 " + callAmount + "을 Call 했습니다.");
    }

    @Override
    public void check() {
        System.out.println("Computer가 Check 했습니다.");
    }

    @Override
    public void allIn() {
        int amount = chips;
        chips = 0;
        System.out.println("Computer가 All-in! ( " + amount + " )");
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
    public Action takeAction(int currentBet, int pot, List<Card> communityCards) {

        try { Thread.sleep(900); } catch (Exception e) {}

        boolean preflop = communityCards.size() == 0;

        if (preflop) {
            return decidePreflop(currentBet);
        } else {
            return decidePostFlop(currentBet, communityCards);
        }
    }

    //프리플랍 액션
    private Action decidePreflop(int currentBet) {

        boolean strong = isStrongPreflopHand();

        if (strong) {
            // 강한 핸드
            if (currentBet == 0) {
                // 아무도 베팅 안 함 → Bet (or Raise)
                bet(30);
                return new Action.Bet(30);
            } else {
                // 상대 Raise 있음 → Call
                call(currentBet);
                return new Action.Call(currentBet);
            }
        } else {
            // 약한 핸드
            if (currentBet == 0) {
                check();
                return new Action.Check();
            } else {
                // 상대가 Bet 했으면 Fold
                fold();
                return new Action.Fold();
            }
        }
    }

    //포스트플랍 액션
    private Action decidePostFlop(int currentBet, List<Card> community) {

        int handRank = evaluateHand(community);

        if (handRank >= 3) {
            // Two Pair 이상 → 공격적
            if (currentBet == 0) {
                bet(40);
                return new Action.Bet(40);
            } else {
                call(currentBet);
                return new Action.Call(currentBet);
            }
        }
        else if (handRank >= 1) {
            // One Pair ~ Two Pair 사이 → 보통 공격
            if (currentBet == 0) {
                check();
                return new Action.Check();
            } else {
                call(currentBet);
                return new Action.Call(currentBet);
            }
        }
        else {
            // High card → 약함
            if (currentBet == 0) {
                check();
                return new Action.Check();
            } else {
                fold();
                return new Action.Fold();
            }
        }
    }
}
