import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ZombieTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Zombie zombie = new Zombie(difficulty);

        int expectedHealth = (int)(55 + 9 * difficulty);
        int expectedDamage = (int)(5 + 3 * difficulty);

        assertEquals(expectedHealth, zombie.getHealth());
        assertEquals(expectedDamage, zombie.getDamage());
    }

    @Test
    public void testGetType() {
        Zombie zombie = new Zombie(0);
        assertEquals("Zombie", zombie.getType());
    }

    @Test
    public void testLowDifficultyValues() {
        Zombie zombie = new Zombie(1.0);

        assertEquals(64, zombie.getHealth());
        assertEquals(8, zombie.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Zombie zombie = new Zombie(50.0);

        assertEquals(505, zombie.getHealth());
        assertEquals(155, zombie.getDamage());
    }
}
