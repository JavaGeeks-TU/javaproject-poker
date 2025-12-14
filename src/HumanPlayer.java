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
        chips -=amount;
        System.out.println(name + "이(가) " + amount + "을 Bet");
    }

    @Override
    public void raise(int amount){
        chips -=amount;
        System.out.println(name + "이(가) " + amount + "을 Raise");
    }

    @Override
    public void call(int amount){
        chips -=amount;
        System.out.println(name + "이(가) " + amount + "을 call");
    }

    @Override
    public void check(){
        System.out.println(name + "이(가) Check");
    }

    @Override
    public void allIn(){
        allined =true;
        if(chips == 0){
            System.out.println(name+"의 잔액이 없습니다. 넘어갑니다.");
        }
        else {
            int amount = chips;
            chips = 0;
            System.out.println(name + "이(가) All-in( " + amount + " )");
        }
    }

    @Override
    public Action takeAction(int currentBet, int pot, List<Card> communityCards) {

        if(folded){
            System.out.println("폴드 했습니다.");
            return new Action.Fold();
        }
        else if(chips == 0){
            return new Action.AllIn(chips);
        }
        else {
            System.out.println("\n==== " + name + "의 턴 ====");
            System.out.println("당신의 카드 : "+holdCards);
            System.out.println("현재 칩: " + chips);
            System.out.println("현재 배팅 : " + currentBet);
            System.out.println("가능한 선택:");
            if (currentBet==0) {
                System.out.println("[1] Check");
                System.out.println("[2] Bet");
                System.out.println("[3] All-in");
                System.out.println("[4] Fold");
            } else if (currentBet>chips) {
                System.out.println("현재 잔액이 부족합니다. All-in하시겠습니까?");
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
                    if (currentBet==0) {
                        check();
                        return new Action.Check();
                    } else {
                        call(currentBet);
                        return new Action.Call(currentBet);
                    }
                case 2:
                    System.out.print("베팅 금액 입력: ");
                    int amount;
                    do {
                        amount = sc.nextInt();
                        if (amount < currentBet) {
                            System.out.println("현재 배팅금액보다 낮습니다. 다시 입력해주세요.");
                        }
                        if (amount > chips) {
                            System.out.println("현재 보유금액보다 높습니다. 다시 입력해주세요.");
                        }
                    }while(amount<currentBet);
                    if(currentBet==0){
                        bet(amount);
                        return new Action.Bet(amount);
                    }
                    raise(amount);
                    return new Action.Raise(amount);
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
