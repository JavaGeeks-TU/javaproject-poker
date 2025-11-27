import java.util.List;

public class PrintCard {
    public void printcard(List<Card> cards){
        for(Card card:cards){
            System.out.print(card+" ");
        }
        System.out.println();
    }
}