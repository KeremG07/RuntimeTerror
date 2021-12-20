package domain.ability;
import domain.body.*;
import domain.needForSpear.Controller;
import domain.needForSpear.Statistics;

import java.util.Random;

public class HollowPurple extends Ability {
    public final double gameScreenWidth = Controller.getInstance().getFrameBorders()[0],
            gameScreenHeight = Controller.getInstance().getFrameBorders()[1],
            hollowPurpleNum = 8;
    public Random randi = new Random();
    private static HollowPurple instance;

    private HollowPurple() {
        super();
        //The GameScreen is divided into cells (size: 10x10) where hollow purple obstacles can be put. The objects are
        // put into the empty cells chosen randomly in a way that they won't clash with already existing obstacles.
        boolean[][] locationCells = new boolean[(int) (gameScreenHeight-200)/40][(int)gameScreenWidth/100];
        for(int i=0; i<hollowPurpleNum; i++){
            putHPInCell(locationCells);
        }
    }

    public static HollowPurple getInstance() {
        if(instance == null) {
            instance = new HollowPurple();
        }
        return instance;
    }

    //Finding an empty cell and creating the obstacle there.
    public void putHPInCell(boolean [][]locationCells){
        int column;
        int row;
        column = randi.nextInt(10);
        row = randi.nextInt(10);
        while(locationCells[row][column]){
            column = randi.nextInt(10);
            row = randi.nextInt(10);
        }
        int x = column*100;
        int y = row*40 + 60;
        Statistics.addObstacle(BodyFactory.createObstacle("Hollow",x,y,1));
        locationCells[row][column] = true;
    }
}
