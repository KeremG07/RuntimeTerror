package domain.body;

public class Body {
    protected int x;
    protected int y;
    public int width;
    public int height;

    public Body(int x_coordinates,
                int y_coordinates,
                int width,
                int height) {
        x = x_coordinates;
        y = y_coordinates;
        this.width = width;
        this.height = height;
    }
    
    public int[] getCoordinates(){
        return new int[] {x,y};
    }
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean compareCoordinates(int x, int y, int width, int height) {
        boolean crashingCoordinates = false;
        int x1 = this.x;
        int y1 = this.y;

        int height1 = this.height;
        int width1 = this.width;

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
