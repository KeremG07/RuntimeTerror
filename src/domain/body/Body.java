package domain.body;

public class Body {
    public double x;
    public double y;
    public double length;
    public double width;
    public double vx;
    public double vy;

    public Body(double x_coordinates,
                double y_coordinates,
                double length,
                double width,
                double vx,
                double vy) {
        x = x_coordinates;
        y = y_coordinates;
        this.length = length;
        this.width = width;
        this.vx = vx;
        this.vy = vy;
    }
    
    public void getCoordinates(){
        
    }

    public boolean compareCoordinates(Body body) {
        boolean crashingCoordinates = false;
        double x1 = this.x;
        double y1 = this.y;
        double x2 = body.x;
        double y2 = body.y;

        double width1 = this.width;
        double length1 = this.length;
        double width2 = body.width;
        double length2 = body.length;

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
