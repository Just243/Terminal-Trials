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

        enemies = new ArrayList<Enemy>(); 
    }

    //~Public  Methods ........................................................
    public void newGame() {
        String openingMessage = "Welcome to Termnal Trials. Enter a name for your character to begin";
        System.out.println(openingMessage);
        String playerName = scan.nextLine();
        System.out.println("Hi " + playerName);
        System.out.println();

        player = new Player(playerName, 10, 10, 0); //temp values
    }

    public void createWave() {
        this.currentWave += 1;
        double difficulty = this.currentWave;

        if(currentWave != totalWaves){
            int randomEnemy = rand.nextInt(2);

            for(int i = 1; i > 0; i--) { // loop for multiple enemies, set to 1 for now
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

    public boolean gameActive() {
        return currentWave <= totalWaves;
    }

    public boolean waveActive() {
        return enemies.size() > 0;
    }

    public void printWave() {
        String bar = "------------------";

        System.out.println(bar);
        System.out.println("Current Wave: " + currentWave + "/" + totalWaves);
        System.out.println(bar);

        System.out.println("Enemies:");
        for(Enemy thisEnemy:enemies){
            System.out.print(thisEnemy.getType() + ", ");
        }
        System.out.println();

        System.out.println(bar + "\n");
    }

    public void requestPlayerAction() {
        int playerAction = 0;

        System.out.println("Choose an option");
        System.out.println("1. Attack");
        System.out.println("2. Flee");
        System.out.println();
        while(true) {
            System.out.println("Enter a number from 1-2: ");
            try {
                playerAction = Integer.parseInt(scan.nextLine());
                if(playerAction == 1 || playerAction == 2) {
                    break;
                } else {
                    System.out.println("Invalid choise.");
                }
            } catch (NumberFormatException e) {
                System.out.println("That is not a number.");
            }
        }

        switch(playerAction){
            case 1: //attack
                for (Enemy thisEnemy:enemies) {
                    thisEnemy.setHealth(thisEnemy.getHealth() - player.getDamage());
                }
                if(enemies.get(currentWave - 1).getHealth() <= 0){
                     System.out.println("You defeated the " + enemies.get(currentWave - 1).getType() + "!");
                    enemies.remove(currentWave - 1);
                }
                break;
            case 2: //flee
                // TODO: code for fleeing. Should it just skip to the next wave? or should it redo the current wave idk
                break;
        }
    }
}
