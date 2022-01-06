package domain.body.fallingBody;

import domain.body.Body;
import domain.body.NoblePhantasm;

import java.awt.geom.Point2D;
import java.util.ArrayList;

public abstract class FallingBody extends Body {

    private int vy = 3;
    private NoblePhantasm np;

    public FallingBody(double x_coordinates,
                       double y_coordinates,
                       double width,
                       double height,
                       NoblePhantasm np) {
        super(x_coordinates, y_coordinates, width, height);
        this.np = np;
    }
    public void fall() {
        this.y += vy;
    }

    //improved version of compareCoordinates which takes rotated np into account
    public boolean compareCoordinatesWithNoblePhantasm(){
        double xPosRight, yPosRight;
        ArrayList<Point2D.Double> positions = new ArrayList<Point2D.Double>();
        ArrayList<Double> distances = new ArrayList<Double>();
        if(np.getNormalAngle() <= 1.5 && np.getNormalAngle() >= -1.5){
            return this.compareCoordinates(np.getCoordinates()[0],np.getCoordinates()[1],np.getWidth(),np.getHeight());
        }
        else {
            xPosRight = (np.getCoordinates()[0] + np.getWidth() * Math.cos(Math.toRadians(np.getNormalAngle())));
            yPosRight = (np.getCoordinates()[1] + np.getWidth() * Math.sin(Math.toRadians(np.getNormalAngle())));
            for(int i = 0; i<10000; i++) {
                positions.add(new Point2D.Double(np.getCoordinates()[0] + i * (xPosRight-np.getCoordinates()[0]) / 10000,np.getCoordinates()[1] + i * (yPosRight-np.getCoordinates()[1]) / 10000));
                distances.add(positions.get(i).distance(new Point2D.Double(x+width/2, y+height/2)));
            }
            for(int i=0; i<distances.size(); i++) {
                double dist = distances.get(i);
                if (dist>5.9 && dist<6.1) {
                    return true;
                } else {
                    continue;
                }
            }
            //same with previous, different calculations
        }
        return false;
    }

}
