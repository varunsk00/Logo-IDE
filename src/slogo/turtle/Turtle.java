package slogo.turtle;

public class Turtle {
    public static final double CENTER_X = 0.0;
    public static final double CENTER_Y = 0.0;
    public static final double NORTH = Math.PI/2;

    private double xLocation;
    private double yLocation;
    private double heading;
    private boolean penDown;
    private boolean showTurtle;

    public Turtle(){
        xLocation = CENTER_X;
        yLocation = CENTER_Y;
        heading = NORTH;
        penDown = true;
        showTurtle = true;
    }

    public void move(double pixel){
        xLocation = Math.cos(heading)*pixel + xLocation;
        yLocation = Math.sin(heading)*pixel + yLocation;
    }

    public void rotate(double degree){
        heading = heading + Math.toRadians(degree);
    }

    public void setHeading(double degree){
        heading = Math.toRadians(degree);
    }

    public void towards(double x, double y){
        heading = Math.atan2(y - yLocation, x - xLocation);
    }

    public void showTurtle(boolean showTurtle) {
        this.showTurtle = showTurtle;
    }

    public void goHome(){
        xLocation = CENTER_X;
        yLocation = CENTER_Y;
    }

    public void setXLocation(double xLocation) {
        this.xLocation = xLocation;
    }

    public void setYLocation(double yLocation) {
        this.yLocation = yLocation;
    }

    public void setPenDown(boolean penDown) {
        this.penDown = penDown;
    }

    public double getXLocation() {
        return xLocation;
    }

    public double getYLocation() {
        return yLocation;
    }

    public boolean isPenDown() {
        return penDown;
    }

    public double getHeading() {
        return Math.toDegrees(heading);
    }

    public boolean isShowTurtle() {
        return showTurtle;
    }
}