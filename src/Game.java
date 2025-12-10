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

    public Game(){
        deck = new Deck();
        rules =new Rules();
        kicker = new Kicker();
        sc =  new Scanner(System.in);
        ais = new ArrayList<>();
        players = new ArrayList<>();
        printCard = new PrintCard();

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
        for(int i =0; i<2; i++){
            for(int j = 0; j<players.size(); j++){
                players.get(j).setHoldCards(deck.drawCard());
            }
        }//tlfj
    }
}
