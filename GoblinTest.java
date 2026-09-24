public class GoblinTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Goblin goblin = new Goblin(difficulty);

        int expectedHealth = (int)(50 + 5 * difficulty);
        int expectedDamage = (int)(25 + 5 * difficulty);

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

        assertEquals(55, goblin.getHealth());
        assertEquals(30, goblin.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Goblin goblin = new Goblin(50.0);

        assertEquals(300, goblin.getHealth());
        assertEquals(275, goblin.getDamage());
    }
}
