package domain.needForSpear;

import domain.body.obstacle.Obstacle;

import java.util.ArrayList;

public class Statistics {
    public static double score;
    public static int chances;
    public static ArrayList<Obstacle> obstacleList;
    public static double timeElapsed;
    
    public static void addObstacle(Obstacle obs){
        obstacleList.add(obs);
    }
    public static void removeObstacle(Obstacle obs){
        obstacleList.remove(obs);
    }
    
    public static void buildGame(){
        
    }

}
