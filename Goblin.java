public class Goblin extends Enemy{

Final int baseHealth;
Final int baseDamage;

public Goblin(double difficulty, int health, int damage) {
    this.baseHealt = 50;
    this.baseDamage = 30;
    super(health, damage);
    int health = baseHealth * difficulty;
    int damage = baseDamage * difficulty;







}








}