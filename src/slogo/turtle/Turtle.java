package slogo.turtle;

public class Turtle {
    public static final double CENTER_X = 0.0;
    public static final double CENTER_Y = 0.0;
    public static final double NORTH = 0;

    private double xLocation;
    private double yLocation;
    private double heading;
    private boolean penDown;
    private boolean showTurtle;
    private boolean clearScreen;

    public Turtle(){
        xLocation = CENTER_X;
        yLocation = CENTER_Y;
        heading = NORTH;
        penDown = true;
        showTurtle = true;
    }

    public void move(double pixel){
        xLocation = Math.sin(Math.toRadians(heading))*pixel + xLocation;
        yLocation = -Math.cos(Math.toRadians(heading))*pixel + yLocation;
    }

    public void rotate(double degree){
        setHeading(heading + degree);
    }

    public void setHeading(double degree){
        heading = degree % 360;
    } //FIX MAGIC NUMBER

    public void towards(double x, double y){
        setHeading((Math.atan2(-(x-xLocation), y-yLocation)*(180/Math.PI))+180);  //FIX MAGIC NUMBER
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
        return heading;
    }

    public boolean isShowTurtle() {
        return showTurtle;
    }

    public boolean isCleared(){
        return clearScreen;
    }

    public void setCleared(boolean clear) {
        clearScreen = clear;
    }
}
