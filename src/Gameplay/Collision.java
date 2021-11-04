package Gameplay;
import Body.*;
import Body.Obstacle.Obstacle;
import java.lang.Math;

public class Collision {

    public static void main(String args[]){
        Body obstacle1 = new Obstacle(50,50, 5, 20);
        Body ES = new EnchantedSphere(100, 50, 16, 16);
        Body NP = new NoblePhantasm(100, 34, 8, 64);
        System.out.println("Do obstacle1 and the Enchanted Sphere crash? " + compareCoordinates(obstacle1, ES));
        System.out.println("Do Noble Phantasm and the Enchanted Sphere crash? " + compareCoordinates(NP, ES));
        System.out.println("Do obstacle1 and the Noble Phantasm crash? " + compareCoordinates(obstacle1, NP));
    }

    public static boolean compareCoordinates(Body body1, Body body2){
        boolean crashingCoordinates = false;
        int x1 = body1.x;
        int y1 = body1.y;
        int x2 = body2.x;
        int y2 = body2.y;

        int width1 = body1.width;
        int length1 = body1.length;
        int width2 = body2.width;
        int length2 = body2.length;

        if (Math.abs(x2-x1) <= Math.max(width1,width2)){
            if (y2 >= y1){
                if((y2-y1) <= length2) {
                    crashingCoordinates = true;
                }
            } else {
                if((y1-y2) <= length1) {
                    crashingCoordinates = true;
                }
            }
        } else if (Math.abs(y2-y1) <= Math.max(length1,length2)){
            if (x2 >= x1){
                if((x2-x1) <= width2) {
                    crashingCoordinates = true;
                }
            } else {
                if((x1-x2) <= width1) {
                    crashingCoordinates = true;
                }
            }
        }
        return crashingCoordinates;
    }
}


