package domain.needForSpear;

import domain.body.obstacle.*;
import ui.swing.GameScreen;
import ui.swing.PlayModeFrame;

public class Controller {
    private static Controller instance;
    //How often the movements on the screen is updated.
    public final static int ticksPerSecond = 30;

    Player player;
    Statistics statistics;

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

    //This method will be called ticksPerSecond per second.
    public void updateMovementNP(String npAction){
        player.moveNoblePhantasm(npAction);
        player.updateEnchantedSphere();
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
        return BuildGame.getInstance();
    }

    public GameScreen buildGame() {
        return GameScreen.getInstance();
    }

    public void startGame() {

    }


    public void endGame() {

    }

    public double[] getFrameBorders() {
        double[] borders = new double[2];
        borders[0] = BuildGame.getInstance().width;
        borders[1] = BuildGame.getInstance().height;
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

    public Obstacle addObstacle(String typeOfObstacle, int x, int y) {
        Obstacle createdObstacle;
        if(typeOfObstacle.equals("Simple")){
            createdObstacle = new SimpleObstacle(x, y, 20, player.getNoblePhantasm().width/5, 1);
        }
        else if(typeOfObstacle.equals("Firm")){
            createdObstacle = new FirmObstacle(x, y, 20, player.getNoblePhantasm().width/5, 3);
        }
        else if(typeOfObstacle.equals("Explosive")){
            createdObstacle = new ExplosiveObstacle(x, y, 15, 15, 1);
        }
        else {
            createdObstacle = new GiftObstacle(x, y, 20, player.getNoblePhantasm().width/5, 1, "chance");
        }
        Statistics.addObstacle(createdObstacle);
        return createdObstacle;
    }

    public void destroyObstacle(Obstacle obstacle) {
        Statistics.removeObstacle(obstacle);
    }
}
