import java.util.Random;

public class AIPlayer extends Player{
    private Random random;

    public AIPlayer(String name, int initialChips){
        super(name, initialChips);
        this.random = new Random();
    }

    /**
    *AI의 행동
     * @param highestBet 현재 라운드의 가장 높은 베팅 금액
     * @param minimumRaise 최소 레이즈 금액
     * @param pot 현재 팟 크기
     * @return "fold", "call", "raise"
     */

    public String decideAction(int highestBet, int minimumRaise, int pot){
        if (isFolded()) return "fold";

        //랜던 기반 핸드 강도 (0~99)
        int strength=random.nextInt(100);

        //콜 해야 하는 칩 차액
        int callAmount=highestBet - getCurrentBet();

        //AI난이도: 기본

        //강한 핸드 -> raise
        if (strength>80 && getChips() > callAmount+minimumRaise){
            int raiseAmount=minimumRaise;
            bet(callAmount+raiseAmount);
            System.out.println(getName()+"(AI) choose: Raise"+raiseAmount);
            return "raise";
        }

        //중간 핸드 -> call
        if (strength>40){
            bet(callAmount);
            System.out.println(getName()+"(AI) choose: call"+callAmount);
            return "call";
        }

        //약한 핸드 -> fold
        fold();
        System.out.println(getName()+"(AI) choose: fold");
        return "fold";
    }
}
