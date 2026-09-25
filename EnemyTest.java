import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class EnemyTest {

    @Test
    public void testConstructorAndGetters() {
        Enemy enemy = new Enemy(100, 20);

        assertEquals(100, enemy.getHealth());
        assertEquals(20, enemy.getDamage());
    }

    @Test
    public void testSetHealth() {
        Enemy enemy = new Enemy(100, 20);
        enemy.setHealth(150);

        assertEquals(150, enemy.getHealth());
    }

    @Test
    public void testSetDamage() {
        Enemy enemy = new Enemy(100, 20);
        enemy.setDamage(35);

        assertEquals(35, enemy.getDamage());
    }

    @Test
    public void testAttackReducesPlayerHealth() {
        Player player = new Player("Test", 100, 10, 0);
        Enemy enemy = new Enemy(100, 20);

        enemy.attack(player);

        assertEquals(80, player.getHealth());
    }

    @Test
    public void testGetType() {
        Enemy enemy = new Enemy(100, 20);

        assertEquals("Enemy", enemy.getType());
    }

    @Test
    public void testDie() {
        Enemy enemy = new Enemy(100, 20);

        enemy.die();

        assertEquals(0, enemy.getHealth());
        assertTrue(enemy.dead());
    }

    @Test
    public void testDead() {
        Enemy enemy = new Enemy(100, 20);
        assertFalse(enemy.dead());

        enemy.setHealth(0);
        assertTrue(enemy.dead());

        enemy.setHealth(-10);
        assertTrue(enemy.dead());
    }
}
