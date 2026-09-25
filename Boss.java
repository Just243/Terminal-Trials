public class Boss extends Enemy {
    static final int baseHealth = 180;
    static final int baseDamage = 12;
    static final int healthPerWave = 2;
    static final int damagePerWave = 2;

    public Boss(double difficulty) {
        super((int)(baseHealth + healthPerWave * difficulty),(int)(baseDamage + damagePerWave * difficulty));
    }

    @Override 
    public String getType(){
        return "super giga ultra boss";
    }
}
