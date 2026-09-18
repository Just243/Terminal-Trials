package terminalTrials;

public class Boss extends Enemy {
    static final int baseHealth = 300;
    static final int baseDamage = 15;

    public Boss(double difficulty) {
        super((int)(baseHealth + 5 * difficulty),(int)(baseDamage + 5 * difficulty));
    }

    @Override 
    public String getType(){
        return "super giga ultra boss";
    }
}
