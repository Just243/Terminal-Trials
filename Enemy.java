package terminalTrials;

public class Enemy {
    //~ Fields ................................................................
    private int health;
    private int damage;
    //~ Constructors ..........................................................
    public Enemy(int health, int damage) {
        this.health = health;
        this.damage = damage;
    }
    //~Public  Methods ........................................................
    public int getHealth() {
        return health;
    }

    public int getDamage() {
        return damage;
    }

    public void die() {
        System.out.println("Enemy defeated");
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void attack(Player player) {
        player.setHealth(player.getHealth() - damage);
        System.out.println("You took " + damage + " damage.");
    }

    /* 
    Overwritten by other subclasses, idk if this is the best way to do it
    Could also just make this a String parameter, but might as well
    use @Override since it was introduced in the class
    */
    public String getType() {
        return "Enemy";
    }
}
