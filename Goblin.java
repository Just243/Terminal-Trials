public class Goblin extends Enemy {

    static final int baseHealth = 35;
    static final int baseDamage = 8;
    static final int healthPerWave = 7;
    static final int damagePerWave = 3;

    public Goblin(double difficulty) {
        super((int)(baseHealth + healthPerWave * difficulty), (int)(baseDamage + damagePerWave * difficulty));

    }

    @Override 
    public String getType(){
        return "Goblin";
    }

}