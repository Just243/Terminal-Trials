package terminalTrials;

public class Main {

    private static Game mainGame;

    public static void main(String[] args) {
        mainGame = new Game(10);

        while(mainGame.gameActive()){
            mainGame.createWave();
            
        }
    }

}
