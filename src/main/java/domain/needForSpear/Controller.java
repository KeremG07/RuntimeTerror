package domain.needForSpear;

import domain.body.EnchantedSphere;
import domain.body.NoblePhantasm;
import domain.body.fallingBody.FallingBody;
import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.body.obstacle.*;
import ui.swing.GameScreen;
import ui.swing.PlayModeFrame;

import java.util.ArrayList;

public class Controller {
    private static Controller instance;
    //How often the movements on the screen is updated.
    public final static int ticksPerSecond = 30;
    KeyHandler handler = new KeyHandler();
    Player player;
    Statistics statistics;
    StartGame newGame;
    BuildGame buildGame;
    //To keep the track of the obstacles that should be removed from the screen.
    ArrayList<Obstacle> toRemoveObs = new ArrayList<>();
    ArrayList<FallingBody> toRemoveFBody = new ArrayList<>();
    //To check whether we shot the enchanted sphere or not:
    boolean playing = false;
    boolean isPaused = false;
    boolean isOver = false;
    public int timeLeft;
    public final int gameScreenWidth = 1000, gameScreenHeight = 600;

    private Controller() {
        player = new Player();
        statistics = player.statistics;
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
    public void saveGame(){
        player.saveGame(statistics.username);
    }

    public boolean loadGame(){
       return player.loadGame(statistics.username);
    }

    public boolean isPaused() {
        return isPaused;
    }

    public void setPaused(boolean paused) {
        isPaused = paused;
    }

    //This needs to be called when the player shoots the enchanted sphere
    public void startPlaying(){
        playing = true;
    }

    public void updateEverything(){
        if(playing){
            shootEnchantedSphere();
            moveEnchantedSphere();
            //If enchanted sphere falls down set up so that it will be shot again by the player.
            if(player.getEnchantedSphere().getCoordinates()[1] + player.getEnchantedSphere().height>=gameScreenHeight){
                playing = false;
                player.updateEnchantedSphere();
            }
        }
        updateObstacleConditions();
        updateFallingBodyConditions();
    }
    //This method will be called by  the handler.
    public void updateMovementNP(String npAction){
        player.moveNoblePhantasm(npAction);
        player.updateEnchantedSphere();
    }

    public void rotateNoblePhantasm(String npAction){
        player.rotateNoblePhantasm(npAction);
    }
    public void moveEnchantedSphere(){
        player.moveEnchantedSphere();
    }
    //This method will be called ticksPerSecond per second and only after player starts playing the game by shooting
    //the enchanted sphere.
    public void shootEnchantedSphere(){
        player.shootEnchantedSphere();
    }
    //This method will be called ticksPerSecond per second.
    public void updateObstacleConditions(){
        //Remove the obstacles that Enchanted Sphere destroyed.
        for(Obstacle obstacle : statistics.getObstacleList()){
            if(obstacle.getNumberOfHits() <= 0){
                toRemoveObs.add(obstacle);
                obstacle.doWhenDestroyed();
            }
        }
        statistics.getObstacleList().removeAll(toRemoveObs);
        toRemoveObs.removeAll(toRemoveObs);
        for(Obstacle obstacle : statistics.getObstacleList()){
            obstacle.move();
        }
    }

    public void updateFallingBodyConditions() {
        for (FallingBody fbody: statistics.getFallingBodyList()) {
            if (fbody instanceof Gift) {
                if(fbody.compareCoordinates(player.getNoblePhantasm().getCoordinates()[0],player.getNoblePhantasm().getCoordinates()[1], player.getNoblePhantasm().width, player.getNoblePhantasm().height)) {
                    toRemoveFBody.add(fbody);
                    // buraya ability eklemesi de gelecek
                } else {
                    fbody.fall();
                }
            } else {
                if (fbody.compareCoordinates(player.getNoblePhantasm().getCoordinates()[0], player.getNoblePhantasm().getCoordinates()[1], player.getNoblePhantasm().width, player.getNoblePhantasm().height)) {
                    toRemoveFBody.add(fbody);
                    player.loseChance();
                } else {
                    fbody.fall();
                }
            }
        }
        statistics.getFallingBodyList().removeAll(toRemoveFBody);
        toRemoveFBody.removeAll(toRemoveFBody);
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
}
