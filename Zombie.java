public class Zombie extends Enemy {

    static final int baseHealth = 75;
    static final int baseDamage = 10;
    static final int healthPerWave = 2;
    static final int damagePerWave = 2;

    public Zombie(double difficulty) {
        super((int)(baseHealth + healthPerWave * difficulty),(int)(baseDamage + damagePerWave * difficulty));
        
    }

    @Override 
    public String getType(){
        return "Zombie";
    }
}