public class Main {

    private static Game mainGame;

    public static void main(String[] args) {
        mainGame = new Game(10);

        mainGame.newGame();

        while(mainGame.gameActive()){
            mainGame.createWave();
            if(mainGame.getWave() < 11){
            mainGame.printWave();
        }
            while(mainGame.waveActive() && mainGame.getWave() < 11){
                mainGame.requestPlayerAction();
                mainGame.enemyAttack();
            }
        }
    }
}
