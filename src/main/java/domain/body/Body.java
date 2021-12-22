package domain.body;

public class Body {
    protected double x;
    protected double y;
    public double  width;
    public double  height;

    public Body(double x_coordinates,
                double y_coordinates,
                double width,
                double height) {
        x = x_coordinates;
        y = y_coordinates;
        this.width = width;
        this.height = height;
    }
    
    public double[] getCoordinates(){
        return new double[] {x,y};
    }
    public void setCoordinates(double  x, double  y) {
        this.x = x;
        this.y = y;
    }
    public boolean compareCoordinates(double x, double y, double width, double height) {
        boolean crashingCoordinates = false;
        double x1 = this.x;
        double y1 = this.y;

        double height1 = this.height;
        double width1 = this.width;

        if(x >= x1 && x-x1 <= width1 && y >= y1 && y-y1 <= height1){
            crashingCoordinates = true;
        }else if(x >= x1 && x-x1 <= width1 && y < y1 && y1-y <= height){
            crashingCoordinates = true;
        }else if(x < x1 && x1-x <= width && y < y1 && y1-y <= height){
            crashingCoordinates = true;
        }else if(x < x1 && x1-x <= width && y >= y1 && y-y1 <= height1){
            crashingCoordinates = true;
        }

        /*if (Math.abs(x-x1) <= Math.max(width1,width)){
            if (y >= y1){
                if((y-y1) <= height) {
                    crashingCoordinates = true;
                }
            } else {
                if((y1-y) <= height1) {
                    crashingCoordinates = true;
                }
            }
        } else if (Math.abs(y-y1) <= Math.max(height1,height)){
            if (x >= x1){
                if((x-x1) <= width) {
                    crashingCoordinates = true;
                }
            } else {
                if((x1-x) <= width1) {
                    crashingCoordinates = true;
                }
            }
        }*/
        return crashingCoordinates;
    }

}
