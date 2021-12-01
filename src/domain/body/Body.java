package domain.body;

public class Body {
    protected int x;
    protected int y;
    public int length;
    public int width;

    public Body(int x_coordinates,
                int y_coordinates,
                int length,
                int width) {
        x = x_coordinates;
        y = y_coordinates;
        this.length = length;
        this.width = width;
    }
    
    public int[] getCoordinates(){
        return new int[] {x,y};
    }
    public void setCoordinates(int x, int y) {
        this.x = x;
        this.y = y;
    }
    public boolean compareCoordinates(int x, int y, int length, int width) {
        boolean crashingCoordinates = false;
        int x1 = this.x;
        int y1 = this.y;

        int width1 = this.width;
        int length1 = this.length;

        if (Math.abs(x-x1) <= Math.max(width1,width)){
            if (y >= y1){
                if((y-y1) <= length) {
                    crashingCoordinates = true;
                }
            } else {
                if((y1-y) <= length1) {
                    crashingCoordinates = true;
                }
            }
        } else if (Math.abs(y-y1) <= Math.max(length1,length)){
            if (x >= x1){
                if((x-x1) <= width) {
                    crashingCoordinates = true;
                }
            } else {
                if((x1-x) <= width1) {
                    crashingCoordinates = true;
                }
            }
        }
        return crashingCoordinates;
    }

}
