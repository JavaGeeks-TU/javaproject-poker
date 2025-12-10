import java.util.*;

public class Game {

    private Deck deck;
    private HumanPlayer player;
    private Rules rules;
    private Kicker kicker;
    private List<AIPlayer> ais;
    private int startChips = 1000;
    private Scanner sc;
    private List<Player> players;
    private PrintCard printCard;
    private int pot;
    private int currentBet;
    private List<Card> communtiyCards;
    private int dealer = 0;
    private int playersize;
    private int bigblind;
    private int smallblind;

    public Game(){
        deck = new Deck();
        rules =new Rules();
        kicker = new Kicker();
        sc =  new Scanner(System.in);
        ais = new ArrayList<>();
        players = new ArrayList<>();
        printCard = new PrintCard();
        pot = 0;
        currentBet =0;
        communtiyCards = new ArrayList<>();
    }

    public void start(){
        System.out.println("포커게임에 오신걸 환영합니다. \n이름을 입력해주세요.");
        String name = sc.nextLine();
        player = new HumanPlayer(name, startChips, sc);
        System.out.println("플레이를 같이할 AI수를 입력하시오: ");
        int num =  sc.nextInt();
        for(int i = 0; i < num; i++){
            AIPlayer ai= new AIPlayer(startChips);
            ais.add(ai);
        }
    }

    public void play (){
        players.add(player);
        players.addAll(ais);
        playersize = players.size();
        bigblind = (dealer+2)%playersize;
        smallblind = (dealer+1)%playersize;
        for(int i = 0;i<2;i++){
            for(int j = 0;j<playersize;j++){
                players.get((smallblind+j)%playersize).setHoldCards(deck.drawCard());
            }
        }
        System.out.print("당신의 카드 : ");
        printCard.print(player.getHoleCards());
        for(Player p:players){
            Action action = players.get(bigblind).takeAction(currentBet, pot, communtiyCards);
            switch (action) {
                case Action.AllIn allIn -> {
                }
                case Action.Bet bet -> {
                }
                case Action.Call call -> {
                }
                case Action.Check check -> {
                }
                case Action.Fold fold -> {
                }
            }

        }

    }//넹? 구조보니까 머요 왜요 머요 문제 많지?
}
