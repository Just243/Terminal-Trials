public class Main {

    private static Game mainGame;

    public static void main(String[] args) {
        mainGame = new Game(10);

        mainGame.newGame();

        while(mainGame.gameActive()){
            mainGame.createWave();
            mainGame.printWave();
            while(mainGame.waveActive()){
                mainGame.requestPlayerAction();
            }
        }
    }
}
