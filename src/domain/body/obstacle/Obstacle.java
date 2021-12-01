package domain.body.obstacle;
import domain.needForSpear.Controller;
import domain.body.Body;

import java.util.Random;

public abstract class Obstacle extends Body {
    protected boolean moving;
    private int movingProbability;
    private int numberOfHits;
    public Random randi = new Random();

    public Obstacle(double x_coordinates,
                    double y_coordinates,
                    double length,
                    double width,
                    int numberOfHits){
        super(x_coordinates,y_coordinates, length, width);
        movingProbability = randi.nextInt(10);
        moving = movingProbability > 7;
        this.numberOfHits=numberOfHits;
    }
    public abstract void move();

    public int getNumberOfHits(){
        return numberOfHits;
    }
    public void setNumberOfHits(int x){
        numberOfHits = Math.max(x, 0);
    }
}
