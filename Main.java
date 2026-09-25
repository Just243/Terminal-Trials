public class Main {

    private static Game mainGame;

    public static void main(String[] args) {
        mainGame = new Game(10);

        mainGame.newGame();

        while(mainGame.gameActive()){
            if(!mainGame.waveActive()){
                mainGame.createWave();
                mainGame.printWave();
            }
            mainGame.requestPlayerAction();
            mainGame.enemyAttack();
        }

        mainGame.printEndMessage();
    }
}
