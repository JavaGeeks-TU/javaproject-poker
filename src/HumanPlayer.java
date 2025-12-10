import java.util.*;

public class HumanPlayer extends Temp {

    public HumanPlayer(String name, int chips) {
       super(name, chips);
    }

    @Override
    public int bet(int amount){
        amount = Math.min(amount, chips);
        deductChips(amount);
        System.out.println(name + "이(가) " + amount + "을 Bet/Raise");
        return amount;
    }

    @Override
    public int call(int amount){
        int callAmount = Math.min(amount, chips);
        deductChips(callAmount);
        System.out.println(name + "이(가) " + callAmount + "을 call");
        return callAmount;
    }

    @Override
    public int check(){
        System.out.println(name + "이(가) Check");
        return 0;
    }

    @Override
    public int allIn(){
        int amount = chips;
        chips = 0;
        System.out.println(name + "이(가) All-in( " + amount + " )");
        return amount;
    }

    @Override
    public int takeAction(int currentBet, int pot, List<Card> communityCards) {

        System.out.println("\n==== " + name + "의 턴 ====");
        System.out.println("현재 칩: " + chips);
        System.out.println("현재 베팅(BB 대비): " + currentBet);
        System.out.println("가능한 선택:");

        if (currentBet == 0) {
            System.out.println("[1] Check");
            System.out.println("[2] Bet");
            System.out.println("[3] All-in");
            System.out.println("[4] Fold");
        } else {
            System.out.println("[1] Call");
            System.out.println("[2] Raise");
            System.out.println("[3] All-in");
            System.out.println("[4] Fold");
        }

        System.out.print("입력: ");
        int action = sc.nextInt();

        switch (action) {
            case 1:
                if (currentBet == 0) { check(); return 0; }
                else return call(currentBet);
            case 2:
                System.out.print("베팅 금액 입력: ");
                int amount = sc.nextInt();
                return bet(amount);
            case 3:
                return allIn();
            case 4:
                fold();
                return -1;
        }
        return 0;
    }
}
