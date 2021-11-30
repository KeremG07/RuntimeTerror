package domain.needForSpear;

import java.util.ArrayList;

public class BuildGame {
    private static BuildGame instance;
    public void buildGame(){

    }

    public static BuildGame getInstance() {
        if (instance == null)
            instance = new BuildGame();
        return instance;
    }

    public int simpleObstacle = 75, firmObstacle = 10, explosiveObstacle = 5, giftObstacle = 10;
    public int width = 1000, height = 600;

    public void setObstacles(String[] obstacles) {
        try {
            simpleObstacle = Integer.parseInt(obstacles[0]);
        }
        catch(NumberFormatException exception) {
        }
        try {
            firmObstacle = Integer.parseInt(obstacles[1]);
        }
        catch(NumberFormatException exception) {
        }
        try {
            explosiveObstacle = Integer.parseInt(obstacles[2]);
        }
        catch(NumberFormatException exception) {
        }
        try {
            giftObstacle = Integer.parseInt(obstacles[3]);
        }
        catch(NumberFormatException exception) {
        }
    }
}
