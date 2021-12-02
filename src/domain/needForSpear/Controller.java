package domain.needForSpear;

import domain.body.obstacle.*;
import ui.swing.GameScreen;
import ui.swing.PlayModeFrame;

public class Controller {
    private static Controller instance;
    //How often the movements on the screen is updated.
    public final static int ticksPerSecond = 30;
    KeyHandler handler = new KeyHandler();
    Player player;
    Statistics statistics;
    StartGame newGame;
    BuildGame buildGame;
    //To check whether we shot the enchanted sphere or not:
    boolean playing = false;
    boolean isPaused = false;
    boolean isOver = false;
    public int timeLeft;
    public final int gameScreenWidth = 1000, gameScreenHeight = 600;

    private Controller() {
        player = new Player();
        statistics = new Statistics();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }
    //Commented out for test purposes.
    public void startNewGame(String[] numOfObstaclesReq) {
        newGame = new StartGame();
        buildGame = newGame.buildNewGame(numOfObstaclesReq);
    }
    public void startTimer(){

    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    public void startPlaying(){
        playing = true;
    }

    public void updateEverything(){
        //handler.doAction(action);
        updateObstacleConditions();
        if(playing){
            shootEnchantedSphere();
        }
    }
    //This method will be called by  the handler.
    public void updateMovementNP(String npAction){
        player.moveNoblePhantasm(npAction);
        player.updateEnchantedSphere();
    }


    //This method will be called the handler.
    public void rotateNoblePhantasm(String npAction){
        player.rotateNoblePhantasm(npAction);
    }
    //This method will be called ticksPerSecond per second and only after player starts playing the game by shooting
    //the enchanted sphere.
    public void shootEnchantedSphere(){
        player.shootEnchantedSphere();
    }
    ////This method will be called ticksPerSecond per second.
    public void updateObstacleConditions(){
        for(Obstacle obstacle : Statistics.obstacleList){
            if(obstacle.getNumberOfHits() <= 0){
                destroyObstacle(obstacle);
            }
        }
        for(Obstacle obstacle : Statistics.obstacleList){
            obstacle.move();
        }
    }
    public Player getPlayer() {
        return player;
    }
    public Statistics getStatistics() { return statistics; }

    public void endGame() {
        System.exit(0);
    }

    public double[] getFrameBorders() {
        double[] borders = new double[2];
        borders[0] = gameScreenWidth;
        borders[1] = gameScreenHeight;
        return borders;
    }
    public String hitFrame(int x, int y, int width, int height ){
        double screenWidth = getFrameBorders()[0];
        double screenHeight = getFrameBorders()[1];
        if(y <= 0 && x + width >= screenWidth){
            return "UpperRight";
        }
        if(y + height >= screenHeight && x + width >= screenWidth){
            return "DownRight";
        }
        if(y <= 0 && x <= 0){
            return "UpperLeft";
        }
        if(y + height >= screenHeight && x <= 0){
            return "DownLeft";
        }
        if(x + width >= screenWidth){
            return "Right";
        }
        if(y + height >= screenHeight){
            return "Down";
        }
        if(x <= 0){
            return "Left";
        }
        if(y <= 0){
            return "Upper";
        }
        else{
            return "None";
        }
    }

    public void destroyObstacle(Obstacle obstacle) {
        Statistics.removeObstacle(obstacle);
    }
}
