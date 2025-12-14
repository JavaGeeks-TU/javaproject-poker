import java.util.*;

public class AIPlayer extends Player {
    private static int num = 1;
    private Random random = new Random();

    public AIPlayer(int chips) {
        this.name = "Computer " + num++;
        this.chips = chips;
    }

    @Override
    public void bet(int amount){
        chips -=amount;
        System.out.println(name + "이(가) " + amount + "을 Bet");
    }

    @Override
    public void raise(int amount){
        chips -=amount;
        System.out.println(name + "이(가) " + amount + "을 Raise");
    }

    @Override
    public void call(int amount) {
        chips -=amount;
        System.out.println(name+"가 " + amount + "을 Call 했습니다.");
    }

    @Override
    public void check() {
        System.out.println(name+"가 Check 했습니다.");
    }

    @Override
    public void allIn() {
        allined =true;
            int amount = chips;
            System.out.println(name+"가  All-in! ( " + amount + " )");

    }

    //프리플랍 평가 함수
    private boolean isStrongPreflopHand() {
        holdCards.sort(Comparator.comparingInt(card->card.getRank().getValue()));
        if(holdCards.get(0).getRank().getValue() == holdCards.get(1).getRank().getValue()) return true; //페어인지
        if(holdCards.get(0).getSuit() == holdCards.get(1).getSuit()) return true;  //플러시
        if(holdCards.get(0).getRank().getValue()+1 == holdCards.get(1).getRank().getValue()) return true; //스트레이트

        return holdCards.get(0).getRank().getValue() >= 11 || holdCards.get(1).getRank().getValue() >= 11; // A,K,Q,J
    }

    //포스트플랍 족보 평가
    private int evaluateHand(List<Card> community) {
        // 원페어=1, 투페어=2, 트리플=3 ... 같은 형태로 점수화 (단순 버전)
        Rules r = new Rules();
        HandRank rank = r.hankRank(holdCards, community);
        return rank.handRanking().getScore();
    }

    @Override
    public Action takeAction(int currentBet, int pot, List<Card> communityCards) {

        try { Thread.sleep(900); } catch (Exception e) {}

        boolean preflop = communityCards.isEmpty();

        if (preflop) {
            return decidePreflop(currentBet);
        } else if(isFolded()){
            System.out.println(name+"폴드 했습니다.");
            return new Action.Fold();
        } else if(chips == 0 ){
            System.out.println(name+"올인 했습니다.");
            return new Action.Check();
        }else {
            return decidePostFlop(currentBet, communityCards);
        }
    }

    //프리플랍 액션
    private Action decidePreflop(int currentBet) {
        boolean strong = isStrongPreflopHand();

        if (strong) {
            // 강한 핸드
            if (currentBet == 0 ) {
                if (chips <currentBet) {    allIn();  return new Action.AllIn(chips); }
                // 아무도 베팅 안 함 → Bet (or Raise)
                bet(30);
                return new Action.Bet(30);
            } else if (random.nextInt(100)<25) {
                if(chips < currentBet*2){   allIn();    return new Action.AllIn(chips); }
                raise(currentBet*2);
                return new Action.Raise(currentBet*2);
            } else {
                // 상대 Raise 있음 → Call
                if (chips <currentBet) { allIn(); return new Action.AllIn(chips); }
                call(currentBet);
                return new Action.Call(currentBet);
            }
        } else {
            // 약한 핸드
            if (currentBet != 0) {
                if (chips <currentBet) { allIn(); return new Action.AllIn(chips); }
                call(currentBet);
                return new Action.Call(currentBet);
            } else {
                // 상대가 Bet 했으면 Fold
                System.out.println(name+"폴드 했습니다.");
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
                if (chips <currentBet) {    allIn();    return new Action.AllIn(chips); }
                bet(40);
                return new Action.Bet(40);
            } else if (random.nextInt(100)<30) {
                if(chips < currentBet*3){   allIn();    return new Action.AllIn(chips); }
                raise(currentBet * 3);
                return new Action.Raise(currentBet * 3);
            }
            else {
                if (chips <currentBet) {    allIn();    return new Action.AllIn(chips); }
                call(currentBet);
                return new Action.Call(currentBet);
            }
        }
        else if (handRank >= 1) {
            // One Pair ~ Two Pair 사이 → 보통 공격
            if (currentBet == 0) {  check();    return new Action.Check();  }
            else if (random.nextInt(100)<25) {
                if(chips < currentBet*2){   allIn();    return new Action.AllIn(chips); }
                raise(currentBet * 2);
                 return new Action.Raise(currentBet * 2);
            }
            else {
                if (chips <currentBet) {    allIn();    return new Action.AllIn(chips); }
                call(currentBet);   return new Action.Call(currentBet); }
        }
        else {
            // High card → 약함
            if (currentBet == 0) {  check();    return new Action.Check();  }
            else {  System.out.println(name+"폴드 했습니다.");    fold(); return new Action.Fold();   }
        }
    }
}
