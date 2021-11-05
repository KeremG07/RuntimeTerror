package domain.gameplay;

import domain.body.obstacle.Obstacle;


public interface ObstacleMove {
    public void move(Obstacle obstacle, double probability);
}
