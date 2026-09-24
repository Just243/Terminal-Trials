public class Goblin extends Enemy {

    static final int baseHealth = 50;
    static final int baseDamage = 15;

    public Goblin(double difficulty) {
        super((int)(baseHealth + 5 * difficulty), (int)(baseDamage + 5 * difficulty));

    }

    @Override 
    public String getType(){
        return "Goblin";
    }

}