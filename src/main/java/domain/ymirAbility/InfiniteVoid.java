package domain.ymirAbility;
import domain.body.obstacle.Obstacle;
import domain.needForSpear.*;

import java.util.ArrayList;
import java.util.Collections;

public class InfiniteVoid extends YmirAbility {
    ArrayList<Obstacle> obstacles = Controller.getInstance().getStatistics().getObstacleList();
    public InfiniteVoid() {
        super();
        name = "Infinite Void";
        freeze();
    }

    public void freeze(){
        ArrayList<Obstacle> obstaclesLeft = new ArrayList<>();
        for(Obstacle obstacle : obstacles){
            if(!obstacle.getName().equals("Hollow")){
                obstaclesLeft.add(obstacle);
            }
        }
        //Shuffles the list so the first 8 chosen are random.
        Collections.shuffle(obstaclesLeft);
        for(int i=0; i<Math.min(8,obstaclesLeft.size()); i++){
            obstaclesLeft.get(i).setFrozen(true);
        }
    }

    @Override
    public void endDuration(){
        for(Obstacle obstacle : obstacles){
            if(obstacle.isFrozen()){
                obstacle.setFrozen(false);
            }
        }
    }
}
