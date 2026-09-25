import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BossTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Boss boss = new Boss(difficulty);

        int expectedHealth = (int)(300 + 5 * difficulty);
        int expectedDamage = (int)(15 + 5 * difficulty);

        assertEquals(expectedHealth, boss.getHealth());
        assertEquals(expectedDamage, boss.getDamage());
    }

    @Test
    public void testGetType() {
        Boss boss = new Boss(0);
        assertEquals("super giga ultra boss", boss.getType());
    }

    @Test
    public void testLowDifficultyValues() {
        Boss boss = new Boss(1.0);

        assertEquals(305, boss.getHealth());
        assertEquals(20, boss.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Boss boss = new Boss(50.0);

        assertEquals(550, boss.getHealth());
        assertEquals(265, boss.getDamage());
    }
}
