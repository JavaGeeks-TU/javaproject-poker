import java.util.*;

public class Rules {

    private List<Card> cards;

    public HandRank hankRank(List<Card> h, List<Card> p){
        cardlist(h,p);
        Optional<HandRank> RSF = IsRoyalStrightFlush(cards);
        if(RSF.isPresent()){
            return RSF.get();
        }
        Optional<HandRank> SF = IsStrightFlush(cards);
        if(SF.isPresent()){
            return SF.get();
        }
        Optional<HandRank> Four = IsFour(cards);
        if(Four.isPresent()){
            return Four.get();
        }
        Optional<HandRank> Fullhouse = IsFullhouse(cards);
        if(Fullhouse.isPresent()){
            return Fullhouse.get();
        }
        Optional<HandRank> F = IsFlush(cards);
        if(F.isPresent()){
            return F.get();
        }
        Optional<HandRank> S = IsStright(cards);
        if(S.isPresent()){
            return S.get();
        }

        Optional<HandRank> Triple = IsTriple(cards);
        if(Triple.isPresent()){
            return Triple.get();
        }
        Optional<HandRank> Two = IsTwoPair(cards);
        if(Two.isPresent()){
            return Two.get();
        }
        Optional<HandRank> One = IsPair(cards);
        return One.orElseGet(() -> new HandRank(cards, IsHigh(cards), HandRanking.HighCard));
    }
    private void cardlist(List<Card> handcards, List<Card> ccard){
        List<Card> card = new ArrayList<>();
        card.addAll(handcards);
        card.addAll(ccard);
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

    private List<Card> IsHigh(List<Card> cards){
        List<Card> c = new ArrayList<>();
        c.add(cards.get(cards.size()-1));
        return c;
    }

    private Optional<HandRank> IsFlush (List<Card> cards) {
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
                c.add(cards.get(i));
            }
        }
        if(num ==2){
            return Optional.of(new HandRank(cards, c, HandRanking.TwoPair));
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsTriple (List<Card> cards){
        for(int i = 0; i < cards.size() - 2; i++){
            if(cards.get(i).getRank().getValue() == cards.get(i+2).getRank().getValue()){
                    return Optional.of(new HandRank(cards, cards.subList(i,i+2), HandRanking.Triple));
                }

        }
        return Optional.empty();
    }

    private Optional<HandRank> IsFour (List<Card> cards){
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
        List<Card> card = cards;
        List<Card> c = new ArrayList<>();

        //for문 사용 안했을때 : c = cards.stream().distinct().toList();

        for(int i =0; i < cards.size()-1; i++){
            if(cards.get(i).getRank().getValue() != cards.get(i+1).getRank().getValue()){
                card.add(cards.get(i));
            }
        }
        if(card.size() <5 )
            return Optional.empty();

        for(int i = 0; i < card.size(); i++){
            if(card.get(i).getRank().getValue()+1 == card.get(i+1).getRank().getValue()){
                num++;
                c.add(card.get(i));
            }
            else {
                num=0;
                c.clear();
            }
        }

        for(int i = 0; i < cards.size()-1; i++){
            if(cards.get(i).getRank().getOthervalue()+1 == cards.get(i+1).getRank().getOthervalue()){
                num++;
                c.add(cards.get(i));
            }
            else{
                num=0;
                c.clear();
            }
        }

        if(num ==4) return Optional.of( new HandRank(cards, c, HandRanking.Straight));
        else return Optional.empty();
    }

    private Optional<HandRank> IsStrightFlush (List<Card> cards){
        Optional<HandRank> flush = IsFlush(cards);
        if(flush.isEmpty()){
            return Optional.empty();
        }
        HandRank flushhand = flush.get();
        List<Card> strightcard = flushhand.rankcard();
        Optional<HandRank> stright = IsStright(strightcard);
        if(stright.isPresent()){
            return Optional.of(new HandRank(cards, strightcard, HandRanking.StraightPlush));
        }
        return Optional.empty();
    }

    private Optional<HandRank> IsRoyalStrightFlush (List<Card> cards){
        Optional <HandRank> SF =  IsStrightFlush(cards);
        if(SF.isEmpty()){
            return Optional.empty();
        }
        List<Card> SFcard = SF.get().rankcard();
        int lastcard = SFcard.size()-1;
        if(SFcard.get(lastcard).getRank().getValue() == 14){
            return Optional.of(new HandRank(cards, SFcard, HandRanking.RoyalStraightPlush));
        }
        return Optional.empty();
    }

}