import java.util.*;

//TIP 코드를 <b>실행</b>하려면 <shortcut actionId="Run"/>을(를) 누르거나
// 에디터 여백에 있는 <icon src="AllIcons.Actions.Execute"/> 아이콘을 클릭하세요.
public class Main {
    public static void main(String[] args) {
        //TIP 캐럿을 강조 표시된 텍스트에 놓고 <shortcut actionId="ShowIntentionActions"/>을(를) 누르면
        // IntelliJ IDEA이(가) 수정을 제안하는 것을 확인할 수 있습니다.
        List<Card> hands = new ArrayList<Card>();
        List<Card> pot = new ArrayList<Card>();
        Deck deck = new Deck();
        PrintCard p = new PrintCard();
        Rules r = new Rules();
        for(int i = 0; i < 2; i++){
            hands.add(deck.drawCard());
        }
        for(int i = 0; i < 5; i++){
            pot.add(deck.drawCard());
        }
        p.printcard(hands);
        p.printcard(pot);

        System.out.print(r.hankRanking(hands, pot).getName());
        System.out.println();
        hands.addAll(pot);
    }
}