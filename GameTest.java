import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Scanner;

public class GameTest {

    /**
     * A Random that always returns the same values, so tests don't
     * depend on luck. nextDouble() controls hit damage and flee success,
     * nextInt() controls flee damage and enemy type.
     */
    private static Random fixedRandom(double doubleValue, int intValue) {
        return new Random() {
            @Override
            public double nextDouble() {
                return doubleValue;
            }

            @Override
            public int nextInt(int bound) {
                return intValue;
            }
        };
    }

    private static Game gameWithPlayer(int waves) {
        Game game = new Game(waves);
        game.player = new Player("Test", 100, 25, 0);
        return game;
    }

    @Test
    public void testConstructorInitializesFields() {
        Game game = new Game(5);

        assertEquals(5, game.getTotalWaves());
        assertEquals(0, game.getWave());
        assertTrue(game.enemies.isEmpty());
        assertFalse(game.hasQuit());
    }

    @Test
    public void testNewGameCreatesPlayer() {
        Game game = new Game(3);

        // Simulate user entering name "Alice"
        game.scan = new Scanner("Alice\n");

        game.newGame();

        assertNotNull(game.player);
        assertEquals("Alice", game.player.getName());
        assertEquals(100, game.player.getHealth());
        assertEquals(25, game.player.getDamage());
        assertEquals(0, game.player.getXP());
        assertEquals(1, game.player.getLevel());
    }

    @Test
    public void testNewGameRejectsBlankName() {
        Game game = new Game(3);

        game.scan = new Scanner("\n   \n  Alice  \n");

        game.newGame();

        assertEquals("Alice", game.player.getName());
    }

    @Test
    public void testNewGameWithNoInput() {
        Game game = new Game(3);

        game.scan = new Scanner("");

        game.newGame();

        assertEquals("Hero", game.player.getName());
        assertTrue(game.hasQuit());
        assertFalse(game.gameActive());
    }

    @Test
    public void testCreateWaveAddsGoblinOrZombie() {
        Game game = new Game(5);

        // Force predictable randomness
        game.rand = new Random(0);

        game.createWave();

        assertEquals(1, game.getWave());
        assertEquals(1, game.enemies.size());

        String type = game.enemies.get(0).getType();
        assertTrue(type.equals("Goblin") || type.equals("Zombie"));
    }

    @Test
    public void testCreateWaveAddsBossOnFinalWave() {
        Game game = new Game(1);

        game.createWave();

        assertEquals(1, game.getWave());
        assertEquals(1, game.enemies.size());
        assertEquals("super giga ultra boss", game.enemies.get(0).getType());
        assertTrue(game.isFinalWave());
    }

    @Test
    public void testGameActive() {
        Game game = gameWithPlayer(3);

        assertTrue(game.gameActive());

        game.createWave();
        game.createWave();
        game.createWave();

        // Boss wave is still in progress
        assertTrue(game.gameActive());

        // Boss defeated, no waves left
        game.enemies.clear();
        assertFalse(game.gameActive());
    }

    @Test
    public void testGameNotActiveWhenPlayerDead() {
        Game game = gameWithPlayer(3);

        game.player.die();

        assertFalse(game.gameActive());
        assertTrue(game.playerDead());
    }

    @Test
    public void testWaveActive() {
        Game game = new Game(3);

        assertFalse(game.waveActive());

        game.enemies.add(new Enemy(50, 10));

        assertTrue(game.waveActive());
    }

    @Test
    public void testRequestPlayerActionAttack() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        // 0.5 is the middle of the hit range, so the hit is exactly 25
        game.rand = fixedRandom(0.5, 0);
        game.scan = new Scanner("1\n");

        game.requestPlayerAction();

        assertEquals(25, game.enemies.get(0).getHealth());

        // Enemy still alive → not removed
        assertEquals(1, game.enemies.size());
    }

    @Test
    public void testAttackDamageRange() {
        Game weakest = gameWithPlayer(3);
        weakest.enemies.add(new Enemy(100, 10));
        weakest.rand = fixedRandom(0.0, 0);
        weakest.scan = new Scanner("1\n");
        weakest.requestPlayerAction();

        // 80% of 25
        assertEquals(80, weakest.enemies.get(0).getHealth());

        Game strongest = gameWithPlayer(3);
        strongest.enemies.add(new Enemy(100, 10));
        strongest.rand = fixedRandom(0.9999, 0);
        strongest.scan = new Scanner("1\n");
        strongest.requestPlayerAction();

        // 120% of 25
        assertEquals(70, strongest.enemies.get(0).getHealth());
    }

    @Test
    public void testRequestPlayerActionAttackKillsEnemy() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(15, 10));

        game.rand = fixedRandom(0.5, 0);
        game.scan = new Scanner("1\n");

        game.requestPlayerAction();

        // Enemy should be removed
        assertEquals(0, game.enemies.size());

        // Player should gain XP, but not enough to level up yet
        assertEquals(10, game.player.getXP());
        assertEquals(1, game.player.getLevel());
    }

    @Test
    public void testInvalidInputIsRetried() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        game.rand = fixedRandom(0.5, 0);
        game.scan = new Scanner("abc\n7\n\n1\n");

        game.requestPlayerAction();

        assertEquals(25, game.enemies.get(0).getHealth());
    }

    @Test
    public void testRequestPlayerActionFleeSucceeds() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        // nextDouble() below the flee chance means success, 3 flee damage
        game.rand = fixedRandom(0.0, 3);
        game.scan = new Scanner("2\n");

        game.requestPlayerAction();

        assertEquals(97, game.player.getHealth());
        assertEquals(0, game.enemies.size());

        // No XP for fleeing
        assertEquals(0, game.player.getXP());
    }

    @Test
    public void testRequestPlayerActionFleeFails() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        // nextDouble() above the flee chance means failure
        game.rand = fixedRandom(0.9, 3);
        game.scan = new Scanner("2\n");

        game.requestPlayerAction();

        assertEquals(100, game.player.getHealth());
        assertEquals(1, game.enemies.size());
    }

    @Test
    public void testCannotFleeBoss() {
        Game game = gameWithPlayer(1);
        game.createWave();
        int bossHealth = game.enemies.get(0).getHealth();

        // Tries to flee (refused), then attacks
        game.rand = fixedRandom(0.0, 0);
        game.scan = new Scanner("2\n1\n");

        game.requestPlayerAction();

        assertEquals(1, game.enemies.size());
        assertEquals(bossHealth - 20, game.enemies.get(0).getHealth());
    }

    @Test
    public void testRequestPlayerActionWithNoInputQuits() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        game.scan = new Scanner("");

        game.requestPlayerAction();

        assertTrue(game.hasQuit());
        assertFalse(game.gameActive());

        // Enemies don't attack after quitting
        game.enemyAttack();
        assertEquals(100, game.player.getHealth());
    }

    @Test
    public void testEnemyAttack() {
        Game game = gameWithPlayer(3);
        game.enemies.add(new Enemy(50, 10));

        game.enemyAttack();

        assertEquals(90, game.player.getHealth());
    }

    @Test
    public void testEnemyAttackCanKillPlayer() {
        Game game = new Game(3);

        game.player = new Player("Test", 5, 25, 0);
        game.enemies.add(new Enemy(50, 10));

        // Should not exit the program, just leave the player dead
        game.enemyAttack();

        assertTrue(game.playerDead());
    }

    @Test
    public void testEnemyAttackWithNoEnemies() {
        Game game = gameWithPlayer(3);

        game.enemyAttack();

        assertEquals(100, game.player.getHealth());
    }

    @Test
    public void testUpgradeIncreasesStats() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 20);

        game.upgrade();

        assertEquals(2, game.player.getLevel());
        assertEquals(100 + Game.LEVEL_HEALTH_BONUS, game.player.getHealth());
        assertEquals(25 + Game.LEVEL_DAMAGE_BONUS, game.player.getDamage());
    }

    @Test
    public void testUpgradeDoesNothingIfXPTooLow() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 10);

        game.upgrade();

        assertEquals(1, game.player.getLevel());
        assertEquals(100, game.player.getHealth());
        assertEquals(25, game.player.getDamage());
    }

    @Test
    public void testUpgradeOnlyOncePerLevel() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 20);

        game.upgrade();
        game.upgrade();

        // Still level 2: the second call needs 40 XP
        assertEquals(2, game.player.getLevel());
        assertEquals(100 + Game.LEVEL_HEALTH_BONUS, game.player.getHealth());
    }

    @Test
    public void testUpgradeMultipleLevels() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 40);

        game.upgrade();

        assertEquals(3, game.player.getLevel());
        assertEquals(25 + 2 * Game.LEVEL_DAMAGE_BONUS, game.player.getDamage());
    }
}
