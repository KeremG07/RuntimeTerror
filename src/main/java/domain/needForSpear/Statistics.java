package domain.needForSpear;

import domain.body.fallingBody.FallingBody;
import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.body.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    private String username;
    private static double score;
    private static int chances;
    private static double timeElapsed;

    private static ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
    private static ArrayList<FallingBody> fallingBodyList = new ArrayList<FallingBody>();

    private HashMap<String, Integer> obstacleNumberbyType; // String "type" --> integer "obstacle number


    public Statistics() {
        obstacleNumberbyType = createObstacleNumberbyTypeMap(obstacleList);
        chances = 3;
        timeElapsed = 0;
    }

    public HashMap<String, Integer> createObstacleNumberbyTypeMap(ArrayList<Obstacle> obstacleList) {
        HashMap<String, Integer> obstacleNumberbyType = new HashMap<>();
        obstacleNumberbyType.put("Simple", 0);
        obstacleNumberbyType.put("Firm", 0);
        obstacleNumberbyType.put("Explosive", 0);
        obstacleNumberbyType.put("Gift", 0);


        for (Obstacle obs : obstacleList) {
            if (obs.getName().equals("Simple"))
                obstacleNumberbyType.put("Simple", obstacleNumberbyType.get("Simple") + 1);
            if (obs.getName().equals("Firm")) obstacleNumberbyType.put("Firm", obstacleNumberbyType.get("Firm") + 1);
            if (obs.getName().equals("Explosive"))
                obstacleNumberbyType.put("Explosive", obstacleNumberbyType.get("Explosive") + 1);
            if (obs.getName().equals("Gift")) obstacleNumberbyType.put("Gift", obstacleNumberbyType.get("Gift") + 1);
        }
        return obstacleNumberbyType;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public static double getScore() {
        return score;
    }

    public static void setScore(double score) {
        Statistics.score = score;
    }
    public static void setChances(int chances) {
        Statistics.chances = chances;
    }

    public static int getChances() {
        return chances;
    }

    public static double getTimeElapsed() {
        return timeElapsed;
    }

    public static void setTimeElapsed(double timeElapsed) {
        Statistics.timeElapsed = timeElapsed;
    }

    public static void setObstacleList(ArrayList<Obstacle> obstacleList) {
        Statistics.obstacleList = obstacleList;
    }

    public static ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public static ArrayList<FallingBody> getFallingBodyList() {
        return fallingBodyList;
    }

    public static void setFallingBodyList(ArrayList<FallingBody> fallingBodyList) {
        Statistics.fallingBodyList = fallingBodyList;
    }

    public static void addGift(Gift gift) {fallingBodyList.add(gift);};
    public static void removeGift(Gift gift) {fallingBodyList.remove(gift);};
    public static void addRemains(Remains remains) {fallingBodyList.add(remains);};
    public static void removeRemains(Remains remains) {fallingBodyList.remove(remains);};

    public static void addObstacle(Obstacle obs) {
        obstacleList.add(obs);
    }
    public static void removeObstacle(Obstacle obs) {
        obstacleList.remove(obs);
    }


    public String obstacleTypeList() {
        return obstacleNumberbyType.get("Simple") + "/" + obstacleNumberbyType.get("Firm") + "/"
                + obstacleNumberbyType.get("Explosive") + "/" + obstacleNumberbyType.get("Gift");
    }
}
