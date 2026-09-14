public class Zombie extends Enemy{
Final int baseDamage;
final int baseHealth;

public Zombie(double difficulty,iknt health, int damage) {
    this.baseDamage = 20;
    this.baseHealth = 50;
    super(health, damage);
    this.health = baseHealth + (5 * difficulty);
    this.damage = baseDamage + (5 * difficulty);


}



}