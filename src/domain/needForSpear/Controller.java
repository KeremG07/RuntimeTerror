package domain.needForSpear;
import java.util.*;
public class Controller {
    public static void buildGame() {

    }

    public static void startGame() {

    }

    public static void moveNoblePhantasm() {

    }

    public static void shootEnchantedSphere() {

    }

    public static void endGame() {

    }

    public static void doActions() {

    }

    public static double[][] getFrameBorders() {
        double[][] borders = new double[2][2];
        //borders[0] = UI.corner1;
        //borders[1] = UI.corner2;

        return borders;
    }
    public static String hitFrame(double x, double y, double length, double width ){
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
    public static void addObstacle() {

    }

    public static void destroyObstacle() {

    }
}
