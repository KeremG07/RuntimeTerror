package domain.ability;
import domain.body.obstacle.*;
import domain.needForSpear.Statistics;

import java.util.Random;

public class HollowPurple extends Ability {
    public final int gameScreenWidth = 1000, gameScreenHeight = 600, hollowPurpleNum = 8;
    public Random randi = new Random();
    public HollowPurple() {
        super();
        //The GameScreen is divided into cells (size: 10x10) where hollow purple obstacles can be put. The objects are
        // put into the empty cells chosen randomly in a way that they won't clash with already existing obstacles.
        boolean[][] locationCells = new boolean[(gameScreenHeight-200)/40][gameScreenWidth/100];
        for(int i=0; i<hollowPurpleNum; i++){
            putHPInCell(locationCells);
        }
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
        int y = row*40 + 20;
        HollowPurpleObs newHP = new HollowPurpleObs(x, y, 80, 8, 1);
        Statistics.addObstacle(newHP);
        locationCells[row][column] = true;
    }
}
