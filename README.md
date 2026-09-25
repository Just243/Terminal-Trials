# Terminal-Trials
A text based adventure game for Project 1 in CS 2114

## How to play

Fight your way through 10 waves of enemies. Each wave has a Goblin or a
Zombie, and wave 10 is the boss. Enemies get stronger every wave.

Each turn you choose to:

1. **Attack**: hit the enemy for 80-120% of your damage stat. Every kill
   gives 10 XP, and every 20 XP levels you up (+60 health, +12 damage).
2. **Flee**: a 50% chance to escape the wave for up to 4 damage. If it
   fails, the enemy gets a free hit. Fleeing gives no XP, and you can't
   flee the boss.

Beat the boss to win. Balance values live as constants at the top of
`Game.java` and in each enemy class.

## Running the game

Requires Java 11 or newer.

```bash
javac Main.java Game.java Player.java Enemy.java Goblin.java Zombie.java Boss.java
java Main
```

## Running the tests

The tests use JUnit 5. In VS Code with the Extension Pack for Java, open
the Testing panel and click **Run Tests**.

From the command line, download the
[JUnit Platform Console Standalone](https://central.sonatype.com/artifact/org.junit.platform/junit-platform-console-standalone)
jar, then run:

```bash
javac -cp junit-platform-console-standalone.jar *.java
java -jar junit-platform-console-standalone.jar -cp . --scan-class-path
```
