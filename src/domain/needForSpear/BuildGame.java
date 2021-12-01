package domain.needForSpear;

import domain.body.obstacle.*;


public class BuildGame {
    public int simpleObstacle = 75, firmObstacle = 10, explosiveObstacle = 5, giftObstacle = 10;
    public int width = 1000, height = 600;
    private static BuildGame instance;

    public static BuildGame getInstance() {
        if (instance == null)
            instance = new BuildGame();
        return instance;
    }

    private BuildGame(){

    }


    public Obstacle addObstacle(String typeOfObstacle, int x, int y) {
        Obstacle createdObstacle;
        if(typeOfObstacle.equals("Simple")){
            createdObstacle = new SimpleObstacle(x, y, 20, Controller.getInstance().getPlayer().getNoblePhantasm().width/5, 1);
        }
        else if(typeOfObstacle.equals("Firm")){
            createdObstacle = new FirmObstacle(x, y, 20, Controller.getInstance().getPlayer().getNoblePhantasm().width/5, 3);
        }
        else if(typeOfObstacle.equals("Explosive")){
            createdObstacle = new ExplosiveObstacle(x, y, 15, 15, 1);
        }
        else {
            createdObstacle = new GiftObstacle(x, y, 20, Controller.getInstance().getPlayer().getNoblePhantasm().width/5, 1, "chance");
        }
        Statistics.addObstacle(createdObstacle);
        return createdObstacle;
    }

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
