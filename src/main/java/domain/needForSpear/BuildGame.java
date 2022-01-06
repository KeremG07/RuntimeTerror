package domain.needForSpear;

import domain.body.BodyFactory;
import domain.body.obstacle.*;


import java.util.Random;


public class BuildGame {
    public final int simpleObstacleReq = 75, firmObstacleReq = 10, explosiveObstacleReq = 5, giftObstacleReq = 10;
    private int simpleObstacle, firmObstacle, explosiveObstacle, giftObstacle;
    public final double gameScreenWidth = Controller.getInstance().getFrameBorders()[0],
            gameScreenHeight = Controller.getInstance().getFrameBorders()[1];
    private Random randi = new Random();
    private Statistics statistics;
    //The GameScreen is divided into cells (size: 11x10) where objects can be put.
    private boolean[][] locationCells = new boolean[(int)(gameScreenHeight-160)/40][(int) gameScreenWidth/100];
    //Creates however many obstacles were asked to be created.
    public BuildGame(String[] numOfObstaclesReq){
        //REQUIRES: A string array of length 4.
        //MODIFIES: obstacleList, an ArrayList that contains all the obstacles created in Statistics.java.
        //EFFECTS: Initializes simpleObstacle, firmObstacle, explosiveObstacle and giftObstacle according to user input,
        //creates correct number of obstacles and puts them on the screen randomly and in a way that they do not collide.
        statistics = Controller.getInstance().getStatistics();
        setNumObstacles(numOfObstaclesReq);
        for(int i=0; i<simpleObstacle; i++){
            putObstacleInCell(locationCells, "Simple");
        }
        for(int i=0; i<firmObstacle; i++){
            putObstacleInCell(locationCells, "Firm");
        }
        for(int i=0; i<giftObstacle; i++){
            putObstacleInCell(locationCells, "Gift");
        }
        for(int i=0; i<explosiveObstacle; i++){
            putObstacleInCell(locationCells, "Explosive");
        }
    }
    public void addSimpleObstacle() {
        boolean notOverlaps = true;
        int x;
        int y;
        while (notOverlaps) {
            x = randi.nextInt(900);
            y = randi.nextInt(392);
            for (Obstacle o: statistics.getObstacleList()) {
                if (o.compareCoordinates(x,y, 100, 8)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                statistics.addObstacle(BodyFactory.createObstacle("Simple",x,y,1));
                return;
            } else {
                addSimpleObstacle();
            }
        }
    }
    public void deleteSimpleObstacle(boolean [][]locationCells, Obstacle obstacle) {
        String type = obstacle.getName();
        double x = obstacle.getCoordinates()[0];
        double y = obstacle.getCoordinates()[0];
        int currentSimpleObs = 0;
        for(Obstacle iterObs : statistics.getObstacleList()){
            if(iterObs.getName().equals("Simple")){
                currentSimpleObs++;
            }
        }
        if(type.equals("Simple")){
            if(currentSimpleObs > simpleObstacleReq){
                locationCells[(int)(x-10)/100][(int)(y-20)/40] = false;
                for(Obstacle iterObs : statistics.getObstacleList()){
                    if(iterObs.equals(obstacle)){
                        statistics.getObstacleList().remove(iterObs);
                        return;
                    }
                }
            }
        }

    }
    public void addFirmObstacle() {
        boolean notOverlaps = true;
        int x;
        int y;
        while (notOverlaps) {
            x = randi.nextInt(900);
            y = randi.nextInt(392);
            for (Obstacle o: statistics.getObstacleList()) {
                if (o.compareCoordinates(x,y, 100, 8)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                int hitNum = randi.nextInt(3) + 3;
                statistics.addObstacle(BodyFactory.createObstacle("Firm",x,y,hitNum));
                return;
            } else {
                addFirmObstacle();
            }
        }
    }
    public void deleteFirmObstacle(boolean [][]locationCells, Obstacle obstacle) {
        String type = obstacle.getName();
        double x = obstacle.getCoordinates()[0];
        double y = obstacle.getCoordinates()[0];
        int currentFirmObs = 0;
        for(Obstacle iterObs : statistics.getObstacleList()){
            if(iterObs.getName().equals("Firm")){
                currentFirmObs++;
            }
        }
        if(type.equals("Firm")){
            if(currentFirmObs > firmObstacleReq){
                locationCells[(int)(x-10)/100][(int)(y-20)/40] = false;
                for(Obstacle iterObs : statistics.getObstacleList()){
                    if(iterObs.equals(obstacle)){
                        statistics.getObstacleList().remove(iterObs);
                        return;
                    }
                }
            }
        }
    }
    public void addExplosiveObstacle() {
        boolean notOverlaps = true;
        int x;
        int y;
        while (notOverlaps) {
            x = randi.nextInt(968);
            y = randi.nextInt(368);
            for (Obstacle o: statistics.getObstacleList()) {
                if (o.compareCoordinates(x,y, 32, 32)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                statistics.addObstacle(BodyFactory.createObstacle("Explosive",x,y,1));
                return;
            } else {
                addExplosiveObstacle();
            }
        }
    }
    public void deleteExplosiveObstacle(boolean [][]locationCells, Obstacle obstacle) {
        String type = obstacle.getName();
        double x = obstacle.getCoordinates()[0];
        double y = obstacle.getCoordinates()[0];
        int currentExplosiveObs = 0;
        for(Obstacle iterObs : statistics.getObstacleList()){
            if(iterObs.getName().equals("Explosive")){
                currentExplosiveObs++;
            }
        }
        if(type.equals("Explosive")){
            if(currentExplosiveObs > explosiveObstacleReq){
                locationCells[(int)(x-35)/100][(int)(y-10)/40] = false;
                for(Obstacle iterObs : statistics.getObstacleList()) {
                    if (iterObs.equals(obstacle)) {
                        statistics.getObstacleList().remove(iterObs);
                        return;
                    }
                }
            }
        }
    }
    public void addGiftObstacle() {
        boolean notOverlaps = true;
        int x;
        int y;
        while (notOverlaps) {
            x = randi.nextInt(900);
            y = randi.nextInt(392);
            for (Obstacle o: statistics.getObstacleList()) {
                if (o.compareCoordinates(x,y, 100, 8)) {
                    notOverlaps = false;
                    break;
                }
            }
            if (notOverlaps) {
                statistics.addObstacle(BodyFactory.createObstacle("Gift",x,y,1));
                return;
            } else {
                addGiftObstacle();
            }
        }
    }
    public void deleteGiftObstacle(boolean[][]locationCells, Obstacle obstacle) {
        String type = obstacle.getName();
        double x = obstacle.getCoordinates()[0];
        double y = obstacle.getCoordinates()[0];
        int currentGiftObs = 0;
        for(Obstacle iterObs : statistics.getObstacleList()){
            if(iterObs.getName().equals("Gift")){
                currentGiftObs++;
            }
        }
        if(type.equals("Gift")){
            if(currentGiftObs > giftObstacleReq){
                locationCells[(int)(x-10)/100][(int)(y-20)/40] = false;
                for(Obstacle iterObs : statistics.getObstacleList()){
                    if(iterObs.equals(obstacle)){
                        statistics.getObstacleList().remove(iterObs);
                        return;
                    }
                }
            }
        }

    }
    //Finding an empty cell and creating the obstacle there.
    public void putObstacleInCell(boolean [][]locationCells, String typeOfObstacle){
        int column;
        int row;
        column = randi.nextInt(10);
        row = randi.nextInt(11);
        while(locationCells[row][column]){
            column = randi.nextInt(10);
            row = randi.nextInt(11);
        }
        int x = 10 + column*100;
        int y = 20 + row*40;
        Obstacle newCreatedObstacle;
        if(typeOfObstacle.equals("Firm")){
            int hitNum = randi.nextInt(3) + 3;
            newCreatedObstacle = BodyFactory.createObstacle(typeOfObstacle, x, y, hitNum);
        } else {
            newCreatedObstacle = BodyFactory.createObstacle(typeOfObstacle, x, y, 1);
            if(typeOfObstacle.equals("Explosive")) {
                newCreatedObstacle.setCoordinates(x + 25, y - 10);
            }
        }
        statistics.addObstacle(newCreatedObstacle);
        locationCells[row][column] = true;
    }

    //Gets the number of obstacles entered as input from the user.
    // THIS METHOD WILL BE EDITED AFTER THE CHANGES IN UI.
    public void setNumObstacles(String[] numOfObstaclesReq) {
        try {
            simpleObstacle = Math.max(Integer.parseInt(numOfObstaclesReq[0]), simpleObstacleReq);
        }
        catch(NumberFormatException exception) {
            simpleObstacle = simpleObstacleReq;
        }
        try {
            firmObstacle = Math.max(Integer.parseInt(numOfObstaclesReq[1]), firmObstacleReq);
        }
        catch(NumberFormatException exception) {
            firmObstacle = firmObstacleReq;
        }
        try {
            explosiveObstacle = Math.max(Integer.parseInt(numOfObstaclesReq[2]), explosiveObstacleReq);
        }
        catch(NumberFormatException exception) {
            explosiveObstacle = explosiveObstacleReq;
        }
        try {
            giftObstacle = Math.max(Integer.parseInt(numOfObstaclesReq[3]), giftObstacleReq);
        }
        catch(NumberFormatException exception) {
            giftObstacle = giftObstacleReq;
        }
        if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle > 110){
            giftObstacle = giftObstacleReq;
            if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle > 110) {
                explosiveObstacle = explosiveObstacleReq;
                if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle > 110){
                    firmObstacle = firmObstacleReq;
                    if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle > 110){
                        simpleObstacle = simpleObstacleReq;
                    }
                }
            }
        }
    }
    public int getSimpleObstacleNum() {
        return simpleObstacle;
    }

    public int getFirmObstacleNum() {
        return firmObstacle;
    }

    public int getExplosiveObstacleNum() {
        return explosiveObstacle;
    }

    public int getGiftObstacleNum() {
        return giftObstacle;
    }

    public boolean[][] getLocationCells() { return locationCells; }
}
