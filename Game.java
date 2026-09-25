import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

public class Game {
    //~ Balance Constants .....................................................
    static final int XP_PER_KILL = 10;
    static final int XP_PER_LEVEL = 20;        // level up every 20 XP
    static final int LEVEL_HEALTH_BONUS = 60;
    static final int LEVEL_DAMAGE_BONUS = 12;
    static final double MIN_HIT_MULTIPLIER = 0.8;  // player hits for 80%-120%
    static final double MAX_HIT_MULTIPLIER = 1.2;  // of their damage stat
    static final double FLEE_SUCCESS_CHANCE = 0.5;
    static final int MAX_FLEE_DAMAGE = 4;

    //~ Fields ................................................................
    private int totalWaves;
    private int currentWave;
    private boolean quit;
    Scanner scan;
    ArrayList<Enemy> enemies;
    Player player;
    Random rand = new Random();

    //~ Constructors ..........................................................
    public Game(int waveCount) {
        scan = new Scanner(System.in);
        totalWaves = waveCount;
        currentWave = 0;
        quit = false;

        enemies = new ArrayList<Enemy>();
    }

    //~Public  Methods ........................................................
    public void newGame() {
        String openingMessage = "Welcome to Terminal Trials. Enter a name for your character to begin";
        System.out.println(openingMessage);

        String playerName = readLine();
        while(playerName != null && playerName.isBlank()) {
            System.out.println("Your name can't be blank. Enter a name:");
            playerName = readLine();
        }
        if(playerName == null) {
            playerName = "Hero";
        }
        playerName = playerName.trim();

        System.out.println("Hi " + playerName);
        System.out.println();

        player = new Player(playerName, 100, 25, 0);
    }

    public void createWave() {
        this.currentWave += 1;
        double difficulty = this.currentWave;

        if(!isFinalWave()){
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

    /**
     * The game keeps going while the player is alive, hasn't quit, and
     * there is either a wave in progress or more waves left to play.
     */
    public boolean gameActive() {
        return !quit && !player.dead() && (waveActive() || currentWave < totalWaves);
    }

    public boolean waveActive() {
        return enemies.size() > 0;
    }

    public boolean isFinalWave() {
        return currentWave == totalWaves;
    }

    public void printWave() {
        String bar = "------------------";

        System.out.println(bar);
        System.out.println("Current Wave: " + currentWave + "/" + totalWaves);
        System.out.println(bar);

        System.out.println("Enemies:");
        System.out.println(describeEnemies());

        System.out.println(bar + "\n");

        System.out.println("Your current health is " + player.getHealth()
            + " (Level " + player.getLevel() + ", " + player.getDamage() + " damage)");

        System.out.println(bar + "\n");
    }

    public void requestPlayerAction() {
        int playerAction = 0;

        System.out.println("Choose an option");
        System.out.println("1. Attack");
        System.out.println("2. Flee" + (isFinalWave() ? " (not possible against the boss)" : ""));
        System.out.println();
        while(true) {
            System.out.println("Enter a number from 1-2: ");
            String input = readLine();
            if(input == null) {
                System.out.println("No more input. Exiting the game.");
                return;
            }
            try {
                playerAction = Integer.parseInt(input.trim());
                if(playerAction == 2 && isFinalWave()) {
                    System.out.println("There is no escaping the boss!");
                } else if(playerAction == 1 || playerAction == 2) {
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
                attack();
                break;
            case 2: //flee
                flee();
                break;
        }
    }

    public void enemyAttack(){
        if(quit || player.dead()){
            return;
        }
        if(enemies.size() > 0){
            Enemy attacker = enemies.get(0);
            attacker.attack(player);
            if(player.dead()){
                System.out.println("You took " + attacker.getDamage() + " damage and were killed by the " + attacker.getType() + ".");
            }
            else{
                System.out.println("You took " + attacker.getDamage() + " damage and have " + player.getHealth() + " health remaining.");
            }
            System.out.println();
        }
    }

    /**
     * Levels the player up once for every XP_PER_LEVEL XP they have
     * earned beyond their current level.
     */
    public void upgrade(){
        while (player.getXP() >= player.getLevel() * XP_PER_LEVEL){
            player.setLevel(player.getLevel() + 1);
            player.setHealth(player.getHealth() + LEVEL_HEALTH_BONUS);
            player.setDamage(player.getDamage() + LEVEL_DAMAGE_BONUS);
            System.out.println("Level up! You are now level " + player.getLevel()
                + " (+" + LEVEL_HEALTH_BONUS + " health, +" + LEVEL_DAMAGE_BONUS + " damage).");
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

    public boolean hasQuit(){
        return quit;
    }

    public void printEndMessage(){
        if(quit){
            System.out.println("Thanks for playing, " + player.getName() + ".");
        } else if(player.dead()){
            System.out.println("Game over! You made it to wave " + currentWave + "/" + totalWaves + ".");
        } else {
            System.out.println("Congratulations " + player.getName() + ", you defeated the boss and survived all " + totalWaves + " waves!");
        }
    }

    //~Private Methods .......................................................
    private void attack() {
        for (Enemy thisEnemy:enemies) {
            int hit = rollHitDamage();
            thisEnemy.setHealth(thisEnemy.getHealth() - hit);
            System.out.println("You hit the " + thisEnemy.getType() + " for " + hit + " damage"
                + " (" + Math.max(0, thisEnemy.getHealth()) + " health left).");
        }
        for (int i = enemies.size() - 1; i >= 0; i--) {
            if(enemies.get(i).getHealth() <= 0){
                System.out.println("You defeated the " + enemies.get(i).getType() + "! (+" + XP_PER_KILL + " XP)");
                enemies.remove(i);
                player.setXP(player.getXP() + XP_PER_KILL);
                upgrade();
            }
        }
    }

    private void flee() {
        if(rand.nextDouble() >= FLEE_SUCCESS_CHANCE){
            System.out.println("You tried to flee, but the " + enemies.get(0).getType() + " blocked your escape!");
            return;
        }
        int damageTaken = rand.nextInt(MAX_FLEE_DAMAGE + 1);
        player.setHealth(player.getHealth() - damageTaken);
        if(!this.player.dead()){
            System.out.println("You fled the battle and took " + damageTaken + " damage.");
        }
        else {
            System.out.println("You were killed when trying to flee the battle");
        }
        enemies.clear();
    }

    private int rollHitDamage() {
        double multiplier = MIN_HIT_MULTIPLIER
            + rand.nextDouble() * (MAX_HIT_MULTIPLIER - MIN_HIT_MULTIPLIER);
        return (int)Math.round(player.getDamage() * multiplier);
    }

    private String describeEnemies() {
        ArrayList<String> descriptions = new ArrayList<String>();
        for(Enemy thisEnemy:enemies){
            descriptions.add(thisEnemy.getType() + " (" + thisEnemy.getHealth()
                + " health, " + thisEnemy.getDamage() + " damage)");
        }
        return String.join(", ", descriptions);
    }

    /**
     * Reads a line of input, or returns null (and quits the game) if
     * the input has ended, e.g. the user pressed Ctrl+D.
     */
    private String readLine() {
        if(!scan.hasNextLine()){
            quit = true;
            return null;
        }
        return scan.nextLine();
    }
}
