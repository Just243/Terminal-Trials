import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class GoblinTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Goblin goblin = new Goblin(difficulty);

        int expectedHealth = (int)(50 + 2 * difficulty);
        int expectedDamage = (int)(15 + 2 * difficulty);

        assertEquals(expectedHealth, goblin.getHealth());
        assertEquals(expectedDamage, goblin.getDamage());
    }

    @Test
    public void testGetType() {
        Goblin goblin = new Goblin(0);
        assertEquals("Goblin", goblin.getType());
    }

    @Test
    public void testLowDifficultyValues() {
        Goblin goblin = new Goblin(1.0);

        assertEquals(52, goblin.getHealth());
        assertEquals(17, goblin.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Goblin goblin = new Goblin(50.0);

        assertEquals(150, goblin.getHealth());
        assertEquals(115, goblin.getDamage());
    }
}
