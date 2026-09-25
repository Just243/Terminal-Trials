public class Zombie extends Enemy {

    static final int baseHealth = 55;
    static final int baseDamage = 5;
    static final int healthPerWave = 9;
    static final int damagePerWave = 3;

    public Zombie(double difficulty) {
        super((int)(baseHealth + healthPerWave * difficulty),(int)(baseDamage + damagePerWave * difficulty));
        
    }

    @Override 
    public String getType(){
        return "Zombie";
    }
}