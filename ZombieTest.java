public class ZombieTest {

    @Test
    public void testConstructorCalculations() {
        double difficulty = 10.0;
        Zombie zombie = new Zombie(difficulty);

        int expectedHealth = (int)(75 + 5 * difficulty);
        int expectedDamage = (int)(15 + 5 * difficulty);

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

        assertEquals(80, zombie.getHealth());
        assertEquals(20, zombie.getDamage());
    }

    @Test
    public void testHighDifficultyValues() {
        Zombie zombie = new Zombie(50.0);

        assertEquals(325, zombie.getHealth());
        assertEquals(265, zombie.getDamage());
    }
}
