public class Player {
    //~ Fields ................................................................
    private String name;
    private int health;
    private int damage;
    private double xp;
    private int level;

    //~ Constructors ..........................................................
    public Player(String name, int health, int damage, double xp) {
        this.name = name;
        this.health = health;
        this.damage = damage;
        this.xp = xp;
        this.level = 1;
    }

    //~Public  Methods ........................................................
    public String getName() {
        return this.name;
    }

    public int getHealth() {
        return this.health;
    }

    public int getDamage() {
        return this.damage;
    }

    public double getXP() {
        return this.xp;
    }

    public int getLevel() {
        return this.level;
    }

    public void setName(String character) {
        this.name = character;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public void setDamage(int damage) {
        this.damage = damage;
    }

    public void setXP(double xp) {
        this.xp = xp;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public void die() {
        this.health = 0;
    }

    public boolean dead() {
        return this.health <= 0;
    }

}
