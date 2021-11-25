package domain.body.obstacle;
import domain.needForSpear.Controller;
import domain.body.Body;

import java.util.Random;

public abstract class Obstacle extends Body {
    public String name;
    public boolean moving;
    public int movingProbability;
    public int numberOfHits;

    public Random randi = new Random();

    public Obstacle(double x_coordinates,
                    double y_coordinates,
                    double length,
                    double width,
                    double vx,
                    double vy,
                    String name,
                    int numberOfHits){
        super(x_coordinates,y_coordinates, length, width, vx, vy);
        this.name=name;
        movingProbability = randi.nextInt(10);
        if(movingProbability<=7){ moving = true; }
        else { moving = false; }
        this.numberOfHits=numberOfHits;
    }
    public abstract void move();

}
