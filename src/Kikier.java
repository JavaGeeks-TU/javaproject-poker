import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class Kikier {
    public Card kikier(HandRank handRank) {
        List<Card> cards = handRank.c();
        for(int i=0; i<handRank.rankcard().size();i++){
            if(handRank.rankcard().get(i) == handRank.c().get(i));{
                cards.remove(handRank.rankcard().get(i));
            }
        }
        if(cards.size()==2){
            cards = handRank.rankcard();
        }
        cards.sort(Comparator.comparingInt(card ->card.getRank().getValue()));
        return cards.get(cards.size()-1);
    }
}