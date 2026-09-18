public class Zombie extends Enemy {

    static final int baseHealth = 75;
    static final int baseDamage = 15;

    public Zombie(double difficulty) {
        super((int)(baseHealth + 5 * difficulty),(int)(baseDamage + 5 * difficulty));
        
    }

    @Override 
    public String getType(){
        return "Zombie";
    }
}