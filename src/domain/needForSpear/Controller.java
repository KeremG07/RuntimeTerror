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
    //The next three lines will be in the method called startNewGame, here for test purposes.
    StartGame newGame = new StartGame();
    String[] numOfObstaclesReq = {"3", "34", "2", "6"};
    BuildGame buildGame = newGame.buildNewGame(numOfObstaclesReq);
    //To check whether we shot the enchanted sphere or not:
    boolean playing = false;
    boolean isPaused = false;
    boolean isOver = false;
    public int timeLeft;

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
        //StartGame newGame = new StartGame();
        //BuildGame buildGame = newGame.buildNewGame(numOfObstaclesReq);
    }
    public void startTimer(){

    }
    public void startPlaying(){
        playing = true;
    }
    public void updateEverything(){
        //handler.doAction(action);
        updateObstacleConditions();
        if(playing){
            updateMovementAfterShoot();
        }
    }
    //This method will be called the handler.
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
    public void updateMovementAfterShoot(){
        player.shootEnchantedSphere();
        player.moveEnchantedSphere();
        for(Obstacle obstacle : Statistics.obstacleList){
            obstacle.move();
        }
    }
    ////This method will be called ticksPerSecond per second.
    public void updateObstacleConditions(){
        for(Obstacle obstacle : Statistics.obstacleList){
            if(obstacle.getNumberOfHits() <= 0){
                destroyObstacle(obstacle);
            }
        }
    }
    public Player getPlayer() {
        return player;
    }
    public Statistics getStatistics() { return statistics; }
    public BuildGame getBuildGame() {
        return buildGame;
    }

    public void endGame() {

    }

    public double[] getFrameBorders() {
        double[] borders = new double[2];
        borders[0] = buildGame.gameScreenWidth;
        borders[1] = buildGame.gameScreenHeight;
        return borders;
    }
    public String hitFrame(double x, double y, double length, double width ){
        double screenWidth = getFrameBorders()[0];
        double screenHeight = getFrameBorders()[1];
        if(y <= 0 && x + width >= screenWidth){
            return "UpperRight";
        }
        if(y + length >= screenHeight && x + width >= screenWidth){
            return "DownRight";
        }
        if(y <= 0 && x <= 0){
            return "UpperLeft";
        }
        if(y + length >= screenHeight && x <= 0){
            return "DownLeft";
        }
        if(x + width >= screenWidth){
            return "Right";
        }
        if(y + length >= screenHeight){
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
