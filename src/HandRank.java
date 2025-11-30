import java.util.List;

public record HandRank(
        List<Card> c,
        List<Card> rankcard,
        HandRanking handRanking
) {
}
