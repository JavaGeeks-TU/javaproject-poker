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
        players.add(player);
        players.addAll(ais);
        playersize = players.size();

        while(isplay) {
            pot = 0;
            currentBet =0;
            communtiyCards.clear();
            int foldcount =0;
            bigblind = (dealer + 2) % playersize;
            smallblind = (dealer + 1) % playersize;
            for (int i = 0; i < 2; i++) {
                for (int j = 0; j < playersize; j++) {
                    players.get((smallblind + j) % playersize).setHoldCards(deck.drawCard());
                }
            }
            System.out.print("당신의 카드 : ");
            printCard.print(player.getHoldCards());

            boolean gameFinishedFlag = false;

            gameLoop: for (int j = 0; j < 4; j++) {
                for(int i =0; i<playersize;i++){
                    if(players.get(i).isFolded()) {
                        foldcount++;
                    }
                }
                if(foldcount == playersize-1){
                    for(Player p : players){
                        if(!p.isFolded()){
                            System.out.println(p.getName()+"가 winner");
                            p.addChips(pot);
                            gameFinishedFlag = true;
                            break gameLoop;
                        }
                    }
                }
                int currentindex =bigblind;
                int lastplayerindex = (bigblind-1)%playersize;
                boolean betend = false;
                int allinnum=0;
                int turnnum=0;
                int allturnnum=0;
                do{
                    Player p = players.get(currentindex);
//                    if(allinnum ==0){
//                        if(currentindex == bigblind){
//                            currentBet = 20;
//                        }
//                        if(currentindex == smallblind){
//                            currentBet = 10;
//                        }
//                    }
                    if(turnnum==playersize){
                        allinnum=0;
                        turnnum =0;
                        allturnnum++;
                    }
                    Action action = p.takeAction(currentBet, pot, communtiyCards);
                    switch (action) {
                        case Action.AllIn allIn -> {
                            pot += allIn.chips();
                            if (currentBet < allIn.chips()) {
                                currentBet = allIn.chips();
                            }
                            p.allinChips();
                            lastplayerindex = currentindex;
                        }
                        case Action.Bet bet -> {
                            currentBet = bet.chips();
                            pot += bet.chips();
                            lastplayerindex = currentindex;
                        }
                        case Action.Raise raise->{
                            currentBet = raise.chips();
                            pot+= raise.chips();
                            lastplayerindex = currentindex;
                        }
                        case Action.Call call -> {
                            pot += call.chips();
                        }
                        case Action.Check check -> {
                        }
                        case Action.Fold fold -> {
                        }
                    }
                    if(p.isFolded() || p.folded){
                        allinnum++;
                    }
                    System.out.println("현재 팟 : "+pot);
                    currentindex = (currentindex+1)%playersize;
                    turnnum++;
                    if(lastplayerindex == currentindex){
                        betend = true;
                    } else if (allinnum == playersize) {
                        betend = true;
                    }

                }while(!betend);
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

            if (!gameFinishedFlag) {
                for (int i = 0; i < playersize; i++) {
                    Player nowplayer = players.get(i);
                    System.out.print(nowplayer.name + " 카드 공개 : ");
                    printCard.print(nowplayer.getHoldCards());
                    nowplayer.setHandRank(rules.hankRank(nowplayer.getHoldCards(), communtiyCards));
                    System.out.println(nowplayer.getHandRank().handRanking().getName());
                }
                Player win = winner(players);
                win.addChips(pot);
                System.out.println(win.getName() + "가 이겼습니다.");
            }
            for(int i=1;i<playersize;i++){
                if(players.get(i).chips==0){
                    players.get(i).addChips(1000);
                }
            }
            System.out.println("다음 라운드 진행합니다.\n\n");
            for (int i = 0; i < playersize; i++) {
                players.get(i).newgame();
            }
            if(player.chips==0){
                isplay=false;
                System.out.println("게임이 끝났습니다. 계속 하시겠습니까? YES[0],NO[1]");
                int YN = sc.nextInt();
                if(YN==0){
                    isplay=true;
                    sc.nextLine();
                    start();
                }
            }

        }

    }

    public Player winner(List<Player> players){
        List<Player> playerList = players;
        playerList.sort(Comparator.comparingInt((player ->player.getHandRank().handRanking().getScore())));
        playerList = playerList.reversed();
        Player winner = playerList.getFirst();
        for(int i = 1; i< playerList.size()-1; i++){
            if(playerList.getFirst().getHandRank() == playerList.get(i).getHandRank()){
                if(kicker.kicker(playerList.get(0).getHandRank()).getRank().getValue() < kicker.kicker(playerList.get(i).getHandRank()).getRank().getValue()){
                    winner = playerList.get(i);
                }
            }
        }
        return winner;
    }
    //아직 남았다
}
