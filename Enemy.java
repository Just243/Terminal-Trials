git pushpackage terminalTrials;
public class Enemy  {
    //~ Fields ................................................................
    private int health;
    private int damage;
    //~ Constructors ..........................................................
    public Enemy(int health, int damage){
        this.health = health;
        this.damage = damage;
    }
    //~Public  Methods ........................................................
    public int getHealth(){
        return health;
    }
    public int getDamage(){
        return damage;
    }
    public void die(){
        System.out.println("Enemy defeated");
    }
    public void setHealth(int health){
        this.health = health;
    }
    public void setDamage(int damage){
        this.damage = damage;
    }
    public void attack(Player player){
        player.setHealth(player.getHealth() - damage);
        System.out.println("You took " + damage + " damage.");
    }
}
