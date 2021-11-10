package domain.body.obstacle;

import domain.body.Body;

public abstract class Obstacle extends Body {
    public String name;
    public boolean moving;
    public double movingProbability;
    public int numberOfHits;

    public Obstacle(double x_coordinates,
                    double y_coordinates,
                    double length,
                    double width,
                    double vx,
                    double vy,
                    String name,
                    boolean moving,
                    double movingProbability,
                    int numberOfHits){
        super(x_coordinates,y_coordinates, length, width,vx,vy);
        this.name=name;
        this.moving=moving;
        this.movingProbability=movingProbability;
        this.numberOfHits=numberOfHits;
    }
    public abstract void move();
    public void compareFrame(){

    }
}
