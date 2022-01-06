package domain.needForSpear;

import domain.body.MagicalHex;
import domain.body.fallingBody.FallingBody;
import domain.body.fallingBody.Gift;
import domain.body.obstacle.*;

import java.util.ArrayList;
import java.util.Random;

public class Controller {
    private static Controller instance;

    //How often the movements on the screen is updated.
    public final static int ticksPerSecond = 30;

    private Player player;
    private Statistics statistics;
    private Ymir ymir;

    private StartGame newGame;
    private BuildGame buildGame;

    //To keep the track of the obstacles that should be removed from the screen.
    private ArrayList<Obstacle> toRemoveObs = new ArrayList<>();
    private ArrayList<FallingBody> toRemoveFBody = new ArrayList<>();

    //To check whether we shot the enchanted sphere or not:
    private boolean playing = false;
    private boolean isPaused = false;

    private final int gameScreenWidth = 1000, gameScreenHeight = 600;

    private Controller() {
        player = new Player();
        statistics = player.getStatistics();
        ymir = new Ymir();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }
    public void startNewGame(String[] numOfObstaclesReq) {
        newGame = new StartGame();
        buildGame = newGame.buildNewGame(numOfObstaclesReq);
    }
    public void startTimer(){
    }
    public void saveGame(){
        player.saveGame(statistics.getUsername());
    }

    public boolean loadGame(){
       return player.loadGame(statistics.getUsername());
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
            moveMagicalHexes();

            //If enchanted sphere falls down set up so that it will be shot again by the player.
            if(player.getEnchantedSphere().getCoordinates()[1] + player.getEnchantedSphere().getHeight()>=gameScreenHeight){
                playing = false;
                player.updateEnchantedSphere();
            }

            ymir.lifecycle();
            player.playerAbilityLifeCycle();
        }
        updateObstacleConditions();
        updateFallingBodyConditions();
    }
    //This method is called by the KeyboardController.
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

    public void moveMagicalHexes(){
        if(!statistics.getMagicalHexList().isEmpty()){
            for(MagicalHex hex: statistics.getMagicalHexList()){
                hex.move();
            }
        }
        statistics.getMagicalHexList().removeAll(statistics.getMagicalHexToBeRemovedList());
        statistics.getMagicalHexToBeRemovedList().clear();
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
            if (obstacle.getNumberOfHits() <= 0){
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
                if (fbody.compareCoordinatesWithNoblePhantasm())  {
                    toRemoveFBody.add(fbody);
                    player.getInventory().addAbility(generateAbility());
                } else {
                    fbody.fall();
                }
            } else {
                if (fbody.compareCoordinatesWithNoblePhantasm()) {
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

    public String generateAbility(){
        Random rand = new Random();
        int num = rand.nextInt(4);
        switch (num){
            case 0: return "DoubleNP";
            case 1: return "MagicalHex";
            case 2: return "Unstoppable";
            case 3: return "Chance";
            default: return "";
        }
    }

    public Player getPlayer() {
        return player;
    }

    public Statistics getStatistics() { return statistics; }

    public Ymir getYmir() { return ymir; }

    public void endGame() {
        System.exit(0);
    }

    public double[] getFrameBorders() {
        double[] borders = new double[2];
        borders[0] = gameScreenWidth;
        borders[1] = gameScreenHeight;
        return borders;
    }
    public String hitFrame(double x, double y, double width, double height ){
        //REQUIRES: x,y coordinates and width, height values of the Body object.
        //MODIFIES: None.
        //EFFECTS: Returns the side of the frame where the Body object exceeds its boundaries.
        if(y <= 0 && x + width >= gameScreenWidth){
            return "UpperRight";
        }
        if(y + height >= gameScreenHeight && x + width >= gameScreenWidth){
            return "DownRight";
        }
        if(y <= 0 && x <= 0){
            return "UpperLeft";
        }
        if(y + height >= gameScreenHeight && x <= 0){
            return "DownLeft";
        }
        if(x + width >= gameScreenWidth){
            return "Right";
        }
        if(y + height >= gameScreenHeight){
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
