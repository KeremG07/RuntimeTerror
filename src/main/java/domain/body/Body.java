package domain.body;

public class Body {
    protected double x;
    protected double y;
    protected double width;
    protected double height;

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
        //REQUIRES: x-y coordinates, width, and height of the body to compare with
        //MODIFIES: none
        //EFFECTS:  returns true if objects collide, false if objects do not collide.
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
        return crashingCoordinates;
    }

    public double getWidth() {
        return width;
    }

    public void setWidth(double width) {
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

}
