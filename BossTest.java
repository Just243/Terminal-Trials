import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

public class BossTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Boss boss = new Boss(difficulty);

        int expectedHealth = (int)(180 + 2 * difficulty);
        int expectedDamage = (int)(12 + 2 * difficulty);

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

        assertEquals(182, boss.getHealth());
        assertEquals(14, boss.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Boss boss = new Boss(50.0);

        assertEquals(280, boss.getHealth());
        assertEquals(112, boss.getDamage());
    }
}
