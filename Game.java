package terminalTrials;
import java.util.Scanner;
import java.util.Random;
public class Game {
    //~ Fields ................................................................
    private int totalWaves;
    private int currentWave;
    Scanner scan;
    ArrayList<Enemy> enemies;
    Player player;

    //~ Constructors ..........................................................
    public Game(){
        scan = new Scanner(System.in);
        totalWaves = 0l
        currentWave = 1;






    }

    //~Public  Methods ........................................................
    public void createWave(){
        this.totalWaves += 1;
        this.currentWave += 1
        int randomNumber = rand.nextInt(2) +1;

        
    }

}
