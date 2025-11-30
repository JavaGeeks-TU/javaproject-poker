import java.util.*;

public class Rules {

    private List<Card> cards;

    public HandRanking hankRanking(List<Card> h, List<Card> p){
        cardlist(h,p);
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

    private Optional<HandRank> IsF (List<Card> cards) {
        cards = cards.stream().sorted(Comparator.comparing(Card::getSuit)).toList();
        for(int i =0; i<3;i++){
            if(cards.get(i).getSuit()==cards.get(i+4).getSuit()){
                List<Card> c = cards.subList(i,i+4);
                c = c.stream().sorted(Comparator.comparing(Card::getRank)).toList();
                return Optional.of(new HandRank(cards ,c, HandRanking.Flush));
            }
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsPair (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 1; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                return Optional.of(new HandRank(cards, cards.subList(i,i+1), HandRanking.OnePair));
            }
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsTwoPair (List<Card> cards){
        int num = 0;
        List<Card> c = new ArrayList<>();
        for(int i = 0; i < cards.size() - 1; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                num++;
                c.add(c.get(i));
            }
        }
        if(num ==2){
            return Optional.of(new HandRank(cards, c, HandRanking.TwoPair));
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsTriple (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 2; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()){
                    return Optional.of(new HandRank(cards, cards.subList(i,i+2), HandRanking.Triple));
                }

        }
        return Optional.empty();
    }

    private Optional<HandRank> IsFour (List<Card> cards){
        int num = 0;
        for(int i = 0; i < cards.size() - 3; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+3).getRank().getValue()){
                return Optional.of(new HandRank(cards, cards.subList(i,i+3), HandRanking.FourCard));
            }
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsFullhouse (List<Card> cards){
        int num = 0;
        List<Card> c = new ArrayList<>();
        for(int i = 0; i < cards.size() -3; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()) {
                num = cards.get(i).getRank().getValue();
                if (num < cards.get(i).getRank().getValue()) {
                    num = cards.get(i).getRank().getValue();
                    c.clear();
                    c.addAll(cards.subList(i, i + 3));
                }
            }
        }
        if(num == 0)  return Optional.empty();
        for(int i = 0; i < cards.size() - 1; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                 if(num != cards.get(i).getRank().getValue()){
                     c.addAll(cards.subList(i,i+2));
                     return Optional.of( new HandRank(cards, c, HandRanking.FullHouse));
                 }
            }
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsStright (List<Card> cards){
        int num =0;
        List<Card> card = new ArrayList<>();
        List<Card> c = new ArrayList<>();

        //for문 사용 안했을때 : c = cards.stream().distinct().toList();

        for(int i =0; i < cards.size(); i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+1).getRank().getValue()){
                cards.remove(i+1);
            }
        }
        if(cards.size() <5 )  return Optional.empty();
        for(int i = 0; i < cards.size(); i++){
            if(cards.get(i).getRank().getValue()+1 == cards.get(i+1).getRank().getValue()){
                num++;
                c.add(cards.get(i));
            }
        }

        if(num ==4) return Optional.of( new HandRank(cards, c, HandRanking.Straight));
        return Optional.empty();
    }
}