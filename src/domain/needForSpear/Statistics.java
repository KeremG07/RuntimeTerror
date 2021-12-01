package domain.needForSpear;

import domain.body.obstacle.Obstacle;

import java.util.ArrayList;

public class Statistics {
    public static double score;
    public static int chances;
    public static double timeElapsed;

    public static ArrayList<Obstacle> obstacleList= new ArrayList<Obstacle>();;

    public Statistics() {
    }

    public static ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public static void addObstacle(Obstacle obs){
        obstacleList.add(obs);
    }
    public static void removeObstacle(Obstacle obs){
        obstacleList.remove(obs);
    }



}
