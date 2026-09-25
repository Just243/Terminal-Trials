import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

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
        String openingMessage = "Welcome to Terminal Trials. Enter a name for your character to begin";
        System.out.println(openingMessage);
        String playerName = scan.nextLine();
        System.out.println("Hi " + playerName);
        System.out.println();

        player = new Player(playerName, 100, 25, 0); //temp values
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
        return currentWave < totalWaves && !this.player.dead();
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
        ArrayList<String> enemyTypes = new ArrayList<String>();
        for(Enemy thisEnemy:enemies){
            enemyTypes.add(thisEnemy.getType());
        }
        System.out.println(String.join(", ", enemyTypes));

        System.out.println(bar + "\n");

        System.out.println("Your current health is " + player.getHealth());

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
                    System.out.println("Invalid choice.");
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
                for (int i = enemies.size() - 1; i >= 0; i--) {
                    if(enemies.get(i).getHealth() <= 0){
                        System.out.println("You defeated the " + enemies.get(i).getType() + "!");
                        enemies.remove(i);
                        player.setXP(player.getXP() + 10);
                        upgrade();
                    }
                }
                break;
            case 2: //flee
                int damageTaken = rand.nextInt(5);
                player.setHealth(player.getHealth() - damageTaken);
                if(!this.player.dead()){
                    System.out.println("You fled the battle and took " + damageTaken + " damage.");
                }
                else {
                    System.out.println("You were killed when trying to flee the battle");
                }
                enemies.clear();
                break;
        }
    }

    public void enemyAttack(){
        if(enemies.size() > 0){
            Enemy attacker = enemies.get(0);
            attacker.attack(player);
            if(player.dead()){
                System.out.println("You took " + attacker.getDamage() + " damage and were killed by the " + attacker.getType() + ".");
            }
            else{
                System.out.println("You took " + attacker.getDamage() + " damage and have " + player.getHealth() + " health remaining.");
            }
        }
    }

    public void upgrade(){
        if (this.player.getXP() >= 10){
            player.setHealth(player.getHealth() + 25);
            player.setDamage(player.getDamage() + 5);
        }
    }

    public int getWave(){
        return currentWave;
    }

    public int getTotalWaves(){
        return totalWaves;
    }

    public boolean playerDead(){
        return player.dead();
    }

    public void printEndMessage(){
        if(player.dead()){
            System.out.println("Game over! You made it to wave " + currentWave + "/" + totalWaves + ".");
        } else {
            System.out.println("Congratulations " + player.getName() + ", you survived all " + totalWaves + " waves!");
        }
    }
}
