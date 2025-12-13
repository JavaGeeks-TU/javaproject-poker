import java.util.*;

public class HumanPlayer extends Player {
    private Scanner sc;

    public HumanPlayer(String name, int chips, Scanner sc){
        this.name = name;
        this.chips = chips;
        this.sc = sc;
    }

    @Override
    public void bet(int amount){
        amount = Math.min(amount, chips);
        deductChips(amount);
        System.out.println(name + "이(가) " + amount + "을 Bet/Raise");
    }

    @Override
    public void call(int amount){
        int callAmount = Math.min(amount, chips);
        deductChips(callAmount);
        System.out.println(name + "이(가) " + callAmount + "을 call");
    }

    @Override
    public void check(){
        System.out.println(name + "이(가) Check");
    }

    @Override
    public void allIn(){
        int amount = chips;
        chips = 0;
        System.out.println(name + "이(가) All-in( " + amount + " )");
    }

    @Override
    public Action takeAction(int currentBet, int pot, List<Card> communityCards) {

        if(folded){
            System.out.println("폴드 했습니다.");
            return new Action.Fold();
        }
        else if(chips == 0){
            System.out.println("All in 하였습니다.");
            return new Action.AllIn(currentBet);
        }
        else {
            System.out.println("\n==== " + name + "의 턴 ====");
            System.out.println("당신의 카드 : "+holdCards);
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
                    if (currentBet == 0) {
                        check();
                        return new Action.Check();
                    } else {
                        call(currentBet);
                        return new Action.Call(currentBet);
                    }
                case 2:
                    System.out.print("베팅 금액 입력: ");
                    int amount = sc.nextInt();
                    bet(amount+currentBet);
                    return new Action.Bet(amount);
                case 3:
                    int chip = chips;
                    allIn();
                    return new Action.AllIn(chip);
                case 4:
                    fold();
                    return new Action.Fold();
                default:
                    return new Action.Check();
        }
        }
    }
}
