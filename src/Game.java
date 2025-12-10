import java.util.*;

public class Game {

    private Deck deck;
    private HumanPlayer player;
    private Rules rules;
    private Kikier kikier;
    private List<AIPlayer> AIPlayers;
    private int startchips = 1000;
    private Scanner sc;
    private List<Player> allplayer;
    private PrintCard printCard;

    public Game(){
        deck = new Deck();
        rules =new Rules();
        kikier = new Kikier();
        sc =  new Scanner(System.in);
        AIPlayers = new ArrayList<>();
        allplayer = new ArrayList<>();
        printCard = new PrintCard();

    }

    public void start(){
        System.out.println("포커게임에 오신걸 환영합니다. \n이름을 입력해주세요.");
        String name = sc.nextLine();
        player = new HumanPlayer(name, startchips);
        System.out.println("플레이를 같이할 AI수를 입력하시오: ");
        int num =  sc.nextInt();
        for(int i = 0; i < num; i++){
            AIPlayer AIs= new AIPlayer(startchips);
            AIPlayers.add(AIs);
        }
    }

    public void play (){
        allplayer.add(player);
        allplayer.addAll(AIPlayers);
        for(int i =0; i<2; i++){
            for(int j = 0; j<allplayer.size(); j++){
                allplayer.get(j).setHoldCards(deck.drawCard());
            }
        }//tlfj
    }
}
