package domain.needForSpear;

import domain.body.fallingBody.FallingBody;
import domain.body.fallingBody.Gift;
import domain.body.fallingBody.Remains;
import domain.body.obstacle.Obstacle;

import java.util.ArrayList;
import java.util.HashMap;

public class Statistics {
    private String username;
    private double score;
    private int chances;
    private long timeElapsed;
    private long startTime;
    private ArrayList<Obstacle> obstacleList = new ArrayList<Obstacle>();
    private ArrayList<FallingBody> fallingBodyList = new ArrayList<FallingBody>();

    private HashMap<String, Integer> obstacleNumberbyType; // String "type" --> integer "obstacle number


    public Statistics() {
        obstacleNumberbyType = createObstacleNumberbyTypeMap(obstacleList);
        chances = 3;
        score = 0;
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

    public double getScore() {
        return score;
    }
    public void setScore(double score) {
        this.score = score;
    }

    public int getChances() {
        return chances;
    }
    public void setChances(int chances) {
        this.chances = chances;
        if(this.chances > 3){
            this.chances = 3;
        }
    }

    public long getTimeElapsed() {
        timeElapsed = System.currentTimeMillis() - startTime;
        return timeElapsed/1000;
    }
    public void setTimeElapsed(long timeElapsed) {
        this.timeElapsed = timeElapsed;
    }

    public long getStartTime() {
        return startTime;
    }
    public void setStartTime(long startTime) {
        this.startTime = startTime;
    }


    public ArrayList<Obstacle> getObstacleList() {
        return obstacleList;
    }

    public ArrayList<FallingBody> getFallingBodyList() {
        return fallingBodyList;
    }

    public void addGift(Gift gift) {fallingBodyList.add(gift);}
    public void addRemains(Remains remains) {fallingBodyList.add(remains);}
    public void addObstacle(Obstacle obs) {
        obstacleList.add(obs);
    }

    public String obstacleTypeList() {
        return obstacleNumberbyType.get("Simple") + "/" + obstacleNumberbyType.get("Firm") + "/"
                + obstacleNumberbyType.get("Explosive") + "/" + obstacleNumberbyType.get("Gift");
    }
}
