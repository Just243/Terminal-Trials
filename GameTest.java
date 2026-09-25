import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.Random;
import java.util.Scanner;

public class GameTest {

    @Test
    public void testConstructorInitializesFields() {
        Game game = new Game(5);

        assertEquals(5, game.getTotalWaves());
        assertEquals(0, game.getWave());
        assertTrue(game.enemies.isEmpty());
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
    }

    @Test
    public void testGameActive() {
        Game game = new Game(3);
        game.player = new Player("Test", 100, 25, 0);

        assertTrue(game.gameActive());

        // Still active until the final wave has been played
        game.createWave();
        game.createWave();
        assertTrue(game.gameActive());

        game.createWave();
        assertFalse(game.gameActive());
    }

    @Test
    public void testGameNotActiveWhenPlayerDead() {
        Game game = new Game(3);
        game.player = new Player("Test", 100, 25, 0);

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
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 0);
        game.enemies.add(new Enemy(50, 10));

        // Simulate user choosing "1" (attack)
        game.scan = new Scanner("1\n");

        game.requestPlayerAction();

        // Enemy should take 25 damage
        assertEquals(25, game.enemies.get(0).getHealth());

        // Enemy still alive → not removed
        assertEquals(1, game.enemies.size());
    }

    @Test
    public void testRequestPlayerActionAttackKillsEnemy() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 0);
        game.enemies.add(new Enemy(20, 10));

        game.scan = new Scanner("1\n");

        game.requestPlayerAction();

        // Enemy should be removed
        assertEquals(0, game.enemies.size());

        // Player should gain XP
        assertEquals(10, game.player.getXP());
    }

    @Test
    public void testRequestPlayerActionFlee() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 0);
        game.enemies.add(new Enemy(50, 10));

        // Predictable flee damage: a Random with the same seed
        // produces the same first value
        game.rand = new Random(0);
        int expectedDamage = new Random(0).nextInt(5);

        game.scan = new Scanner("2\n");

        game.requestPlayerAction();

        assertEquals(100 - expectedDamage, game.player.getHealth());

        // Enemy removed
        assertEquals(0, game.enemies.size());
    }

    @Test
    public void testEnemyAttack() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 0);
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
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 0);

        game.enemyAttack();

        assertEquals(100, game.player.getHealth());
    }

    @Test
    public void testUpgradeIncreasesStats() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 10);

        game.upgrade();

        assertEquals(125, game.player.getHealth());
        assertEquals(30, game.player.getDamage());
    }

    @Test
    public void testUpgradeDoesNothingIfXPTooLow() {
        Game game = new Game(3);

        game.player = new Player("Test", 100, 25, 5);

        game.upgrade();

        assertEquals(100, game.player.getHealth());
        assertEquals(25, game.player.getDamage());
    }
}
