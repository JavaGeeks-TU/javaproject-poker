public sealed interface Action {
    record Bet(int chips) implements Action {}
    record Call(int chips) implements Action {}
    record Check() implements Action {}
    record Fold() implements Action {}
}
