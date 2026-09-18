package terminalTrials;
import java.util.Scanner;
import java.util.ArrayList;
import java.util.Random;

public class Game {
    //~ Fields ................................................................
    private int totalWaves;
    private int currentWave;
    Scanner scan;
    ArrayList<Enemy> enemies;
    Player player;

    Random rand = new Random();

    //~ Constructors ..........................................................
    public Game() {
        scan = new Scanner(System.in);
        totalWaves = 0;
        currentWave = 1;






    }
    public void newGame() {
        Game game1 = new Game();
        String openingMessage = "Welcome to Termnal Trials. Enter a name for your character to begin";
        System.out.println(openingMessage);
        String playerName = scan.nextLine();



    }

    //~Public  Methods ........................................................
    public void createWave() {
        this.totalWaves += 1;
        this.currentWave += 1;
        int randomEnemy = rand.nextInt(2) +1;
        double difficulty = this.currentWave;
        if (randomEnemy == 1)
        {
           Goblin goblin = new Goblin(difficulty);

        }
        else{
            Zombie zombie = new Zombie(difficulty);
        }
    }


    public int getCurrentWave() {
        return this.currentWave;
    }

    public boolean checkStringInput()
    {

    }



}
