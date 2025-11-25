import java.util.*;

public class Rules {

    private List<Card> cards;

    public HandRanking hankRanking(List<Card> h, List<Card> p){
        this.cardlist(h,p);
        if (IsFour(this.cards)){
            return HandRanking.FourCard;
        }
        else if (IsFullhouse(this.cards)) {
            return HandRanking.FullHouse;
        }
        else if(IsF(this.cards)) {
            return HandRanking.Flush;
        }
        else if(IsTriple(this.cards)) {
            return HandRanking.Triple;
        }
        else if(IsTwoPair(this.cards)) {
            return HandRanking.TwoPair;
        }
        else if(IsPair(this.cards)) {
            return HandRanking.OnePair;
        }
        return HandRanking.HighCard;
    }
    private void cardlist(List<Card> handcards, List<Card> potcard){
        List<Card> card = new ArrayList<Card>();
        card.addAll(handcards);
        card.addAll(potcard);
        int n = card.size();
        for(int i = 0; i < card.size()-1; i++){
            for(int j = 1; j < card.size(); j++){
                Card card1 = card.get(j-1);
                Card card2 = card.get(j);
                if(card1.getRank().getValue() > card2.getRank().getValue()){
                    Collections.swap(card, j-1, j);
                }
            }
        }
        this.cards=card;
    }

    public boolean IsF (List<Card> cards) {
        int num = 0;
        for (int i = 0; i < cards.size() - 1; i++) {
            Card card1 = cards.get(i);
            Card card2 = cards.get(i + 1);
            if (card1.getSuit().getSuit() == card2.getSuit().getSuit()) {
                num++;
            }
        }
        if (num>5){
            return true;
        }
        else return false;
    }

    public boolean IsPair (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 1; i++){
            Card card1 = cards.get(i);
            Card card2 = cards.get(i + 1);
            if(card1.getRank().getValue() == card2.getRank().getValue()){
                num++;
            }
        }
        if(num==1){ return true;}
        else return false;
    }
    public boolean IsTwoPair (List<Card> cards){
        int num = 0;
        int num2=0;
        for(int i = 0; i < cards.size() - 1; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                num2=cards.get(i).getRank().getValue();
                num++;
            }

        }
        if(num==2){ return true;}
        else return false;
    }

    public boolean IsTriple (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 2; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()){
                    num++;
                }
            }

        }
        if(num==1){ return true;}
        else return false;
    }
    public boolean IsFour (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 3; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()){
                    if(cards.get(i).getRank().getValue() == cards.get(i+3).getRank().getValue()){
                        num++;
                    }
                }
            }

        }
        if(num==1){ return true;}
        else return false;
    }

    public boolean IsFullhouse (List<Card> cards){
        int num=0;
        int n=0;
        for(int i = 0; i < cards.size() - 2; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()){
                    num++;
                    n=cards.get(i).getSuit().getSuit();
                }
                if(n != cards.get(i).getRank().getValue()){
                    num++;
                }
            }

        }
        if(num==2){ return true;}
        else return false;
    }


}