public class Goblin extends Enemy {

    static final int baseHealth = 50;
    static final int baseDamage = 15;
    static final int healthPerWave = 2;
    static final int damagePerWave = 2;

    public Goblin(double difficulty) {
        super((int)(baseHealth + healthPerWave * difficulty), (int)(baseDamage + damagePerWave * difficulty));

    }

    @Override 
    public String getType(){
        return "Goblin";
    }

}