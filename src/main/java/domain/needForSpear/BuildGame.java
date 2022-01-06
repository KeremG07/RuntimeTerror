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

    //Creates however many obstacles were asked to be created.
    public BuildGame(String[] numOfObstaclesReq){
        //REQUIRES: A string array of length 4.
        //MODIFIES: obstacleList, an ArrayList that contains all the obstacles created in Statistics.java.
        //EFFECTS: Initializes simpleObstacle, firmObstacle, explosiveObstacle and giftObstacle according to user input,
        //creates correct number of obstacles and puts them on the screen randomly and in a way that they do not collide.
        setNumObstacles(numOfObstaclesReq);
        //The GameScreen is divided into cells (size: 10x10) where objects can be put.
        boolean[][] locationCells = new boolean[(int)(gameScreenHeight-200)/40][(int) gameScreenWidth/100];
        for(int i=0; i<simpleObstacle; i++){
            putObstacleInCell(locationCells, "Simple");
        }
        for(int i=0; i<firmObstacle; i++){
            putObstacleInCell(locationCells, "Firm");
        }
        for(int i=0; i<explosiveObstacle; i++){
            putObstacleInCell(locationCells, "Explosive");
        }
        for(int i=0; i<giftObstacle; i++){
            putObstacleInCell(locationCells, "Gift");
        }
    }


    //Finding an empty cell and creating the obstacle there.
    public void putObstacleInCell(boolean [][]locationCells, String typeOfObstacle){
        int column;
        int row;
        column = randi.nextInt(10);
        row = randi.nextInt(10);
        while(locationCells[row][column]){
            column = randi.nextInt(10);
            row = randi.nextInt(10);
        }
        int x = 10 + column*100;
        int y = row*40 + 40;
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
        Controller.getInstance().getStatistics().addObstacle(newCreatedObstacle);
        locationCells[row][column] = true;
    }

    //Gets the number of obstacles entered as input from the user.
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
        if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle >= 100){
            giftObstacle = giftObstacleReq;
            if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle >= 100) {
                explosiveObstacle = explosiveObstacleReq;
                if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle >= 100){
                    firmObstacle = firmObstacleReq;
                    if(simpleObstacle + firmObstacle + explosiveObstacle + giftObstacle >= 100){
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
}
