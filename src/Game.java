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
    private boolean isplay;

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
        isplay = true;
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
        while(isplay) {
            currentBet = 20;
            players.add(player);
            players.addAll(ais);
            playersize = players.size();
            bigblind = (dealer + 2) % playersize;
            smallblind = (dealer + 1) % playersize;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < playersize; j++) {
                    players.get((smallblind + j) % playersize).setHoldCards(deck.drawCard());
                }
            }
            System.out.print("당신의 카드 : ");
            printCard.print(player.getHoldCards());
            for (int j = 0; j < 4; j++) {
                for (int i = 0; i < playersize; i++) {
                    Action action = players.get((bigblind + i) % playersize).takeAction(currentBet, pot, communtiyCards);
                    switch (action) {
                        case Action.AllIn allIn -> {
                            pot += allIn.chips();
                            if (currentBet < allIn.chips()) {
                                currentBet = allIn.chips();
                            }
                        }
                        case Action.Bet bet -> {
                            currentBet = bet.chips();
                            pot += bet.chips();
                        }
                        case Action.Call call -> {
                            pot += call.chips();
                        }
                        case Action.Check check -> {
                        }
                        case Action.Fold fold -> {
                        }
                    }
                }
                if (communtiyCards.size() < 3) {
                    for (int i = 0; i < 3; i++) {
                        if (communtiyCards.size() < 3) {
                            communtiyCards.add(deck.drawCard());
                        }
                    }
                    System.out.print("카드 3장 공개: ");
                    printCard.print(communtiyCards);
                } else if (communtiyCards.size() == 5) {
                    break;
                } else {
                    communtiyCards.add(deck.drawCard());
                    System.out.print("카드 공개: ");
                    printCard.print(communtiyCards);
                }
            }
            for(int i=0;i<playersize;i++){
                Player nowplayer = players.get(i);
                System.out.print(nowplayer.name+" 카드 공개 : ");
                printCard.print(nowplayer.getHoldCards());
                nowplayer.setHandRank(rules.hankRank(nowplayer.getHoldCards(),communtiyCards));
            }
            winner(players);
            if(player.chips==0){
                isplay=false;
                System.out.println("게임이 끝났습니다. 계속 하시겠습니까? YES[0],NO[1]");
                int YN = sc.nextInt();
                if(YN==0){
                    isplay=true;
                    start();
                }
            }

        }

    }

    public Player winner(List<Player> players){
        List<Player> winners = players;
        players.sort(Comparator.comparingInt(player ->player.getHandRank().handRanking().getScore()));

        return winners.get(-1);
    }
    //아직 남았다
}
