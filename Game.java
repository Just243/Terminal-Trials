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
    public Game(int waveCount) {
        scan = new Scanner(System.in);
        totalWaves = waveCount;
        currentWave = 0;






    }

    //~Public  Methods ........................................................
    public void newGame() {
        String openingMessage = "Welcome to Termnal Trials. Enter a name for your character to begin";
        System.out.println(openingMessage);
        String playerName = scan.nextLine();

        player = new Player(playerName, 10, 10, 0); //temp values

        
    }

    public void createWave() {
        this.currentWave += 1;
        double difficulty = this.currentWave;

        if(currentWave != totalWaves){
            int randomEnemy = rand.nextInt(2);

            for(int i = rand.nextInt(2)+1; i > 0; i--) {
                enemies.add(
                    switch (randomEnemy) {
                        case 0 -> new Goblin(difficulty);
                        case 1 -> new Zombie(difficulty);
                        default -> null;
                    }
                );
            }
        } else {
            enemies.add(new Boss(difficulty));
        }
    }


    public int getCurrentWave() {
        return this.currentWave;
    }

    public boolean gameActive() {
        return currentWave <= totalWaves;
    }

    public boolean checkStringInput() {

    }



}
