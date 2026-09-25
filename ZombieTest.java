import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class ZombieTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Zombie zombie = new Zombie(difficulty);

        int expectedHealth = (int)(75 + 2 * difficulty);
        int expectedDamage = (int)(10 + 2 * difficulty);

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

        assertEquals(77, zombie.getHealth());
        assertEquals(12, zombie.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Zombie zombie = new Zombie(50.0);

        assertEquals(175, zombie.getHealth());
        assertEquals(110, zombie.getDamage());
    }
}
