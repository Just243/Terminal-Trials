package terminalTrials;

public class Zombie extends Enemy {

    final int baseHealth = 75;
    final int baseDamage = 15;

    public Zombie(double difficulty) {
        super((int)(baseHealth + 5 * difficulty),(int)(baseDamage + 5 * difficulty));
        
    }
}