import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class PlayerTest {

    public class PlayerTest {

        @Test
        public void testConstructorAndGetters() {
            Player p = new Player("Alice", 100, 15, 10.5);

            assertEquals("Alice", p.getName());
            assertEquals(100, p.getHealth());
            assertEquals(15, p.getDamage());
            assertEquals(10.5, p.getXP());
        }

        @Test
        public void testSetName() {
            Player p = new Player("Bob", 80, 10, 5.0);
            p.setName("Charlie");

            assertEquals("Charlie", p.getName());
        }

        @Test
        public void testSetHealth() {
            Player p = new Player("Bob", 80, 10, 5.0);
            p.setHealth(120);

            assertEquals(120, p.getHealth());
        }

        @Test
        public void testSetDamage() {
            Player p = new Player("Bob", 80, 10, 5.0);
            p.setDamage(25);

            assertEquals(25, p.getDamage());
        }

        @Test
        public void testSetXP() {
            Player p = new Player("Bob", 80, 10, 5.0);
            p.setXP(99.9);

            assertEquals(99.9, p.getXP());
        }

        @Test
        public void testDieMethod() {
            Player p = new Player("Bob", 80, 10, 5.0);
            p.die();

            assertEquals(0, p.getHealth());
        }
    }


}
