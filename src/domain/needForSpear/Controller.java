package domain.needForSpear;

import ui.swing.GameScreen;
import ui.swing.PlayModeFrame;

public class Controller {
    private static Controller instance;
    //How often the movements on the screen is updated.
    public final static int ticksPerSecond = 30;


    Player player;
    boolean isPaused = false;
    boolean isOver = false;
    public int timeLeft;

    private Controller() {
        player = new Player();
    }

    public static Controller getInstance() {
        if (instance == null)
            instance = new Controller();
        return instance;
    }

    public void updateMovement(){
        player.noblePhantasm.moveRight();
        player.enchantedSphere.move();

    }
    public Player getPlayer() {
        return player;
    }

    public BuildGame getBuildGame() {
        return BuildGame.getInstance();
    }

    public GameScreen buildGame() {
        return GameScreen.getInstance();
    }

    public void startGame() {

    }

    public void moveNoblePhantasm() {

    }

    public void shootEnchantedSphere() {

    }

    public void endGame() {

    }

    public void doActions() {

    }

    public double[][] getFrameBorders() {
        double[][] borders = new double[2][2];
        //borders[0] = UI.corner1;
        //borders[1] = UI.corner2;

        return borders;
    }
    public String hitFrame(double x, double y, double length, double width ){
        double[] corner1 = getFrameBorders()[0];
        double[] corner2 = getFrameBorders()[1];
        if(y - length <= corner1[1] && x + width >= corner2[0]){
            return "DownLeft";
        }
        if(y >= corner2[1] && x + width >= corner2[0]){
            return "UpperLeft";
        }
        if(y - length <= corner1[1] && x <= corner1[0]){
            return "DownRight";
        }
        if(y >= corner2[1] && x <= corner1[0]){
            return "UpperRight";
        }
        if(x + width >= corner2[0]){
            return "Left";
        }
        if(y - length <= corner1[1]){
            return "Down";
        }
        if(x <= corner1[0]){
            return "Right";
        }
        if(y >= corner2[1]){
            return "Upper";
        }
        else{
            return "None";
        }
    }

    public double[] getPlayModeFrameBorders() {
        double[] borders = new double[2];

        borders[0] = PlayModeFrame.getInstance().getWidth();
        borders[1] = PlayModeFrame.getInstance().getHeight();

        return borders;
    }

    public void addObstacle() {

    }

    public void destroyObstacle() {

    }
}
