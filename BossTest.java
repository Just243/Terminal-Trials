import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BossTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Boss boss = new Boss(difficulty);

        int expectedHealth = (int)(200 + 2 * difficulty);
        int expectedDamage = (int)(15 + 2 * difficulty);

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

        assertEquals(202, boss.getHealth());
        assertEquals(17, boss.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Boss boss = new Boss(50.0);

        assertEquals(300, boss.getHealth());
        assertEquals(115, boss.getDamage());
    }
}
