package slogo.turtle;

import java.util.ArrayList;
import java.util.List;

public class Turtle {
    public static final double CENTER_X = 0.0;
    public static final double CENTER_Y = 0.0;
    public static final double NORTH = 0;
    public static final double RAD_TO_DEG_RATIO = 180 / Math.PI;

    private double xLocation;
    private double yLocation;
    private double heading;
    private boolean penDown;
    private boolean showTurtle;
    private boolean clearScreen;
    private boolean oldPenDown;
    private boolean isActive;
    private int ID;

    private List<Point> locations;

    public Turtle(){
        xLocation = CENTER_X;
        yLocation = CENTER_Y;
        heading = NORTH;
        penDown = true;
        showTurtle = true;
        locations = new ArrayList<>();
        ID = -1;
    }

    public Turtle(int id) {
        this();
        ID = id;
    }

    public void move(double pixel){
        xLocation = Math.sin(Math.toRadians(heading))*pixel + xLocation;
        yLocation = -Math.cos(Math.toRadians(heading))*pixel + yLocation;
        Point p = new Point(xLocation, yLocation);
        p.setDrawn(penDown);
        locations.add(p);
    }

    public List<Point> locationsList() {
        List<Point> ret = new ArrayList<>(locations);
        locations.clear();
        return ret;
    }

    public void rotate(double degree){
        setHeading(heading + degree);
    }

    public void setHeading(double degree){
        heading = degree % 360;
    }

    public void towards(double x, double y){
        setHeading((Math.atan2(x-xLocation, -(y-yLocation))* RAD_TO_DEG_RATIO));
    }

    public void showTurtle(boolean showTurtle) {
        this.showTurtle = showTurtle;
    }

    public void goHome(){
        xLocation = CENTER_X;
        yLocation = CENTER_Y;
        locations.add(new Point(xLocation,yLocation));
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
        if (clear) {
            oldPenDown = penDown;
            setPenDown(false);
            locations.add(new Point(xLocation,yLocation));

        }
    }

    public void handleClear() {
        setPenDown(oldPenDown);
    }

    public boolean getIsActive() {
        return isActive;
    }

    public void setActive(boolean act) {
        isActive = act;
    }

    public int getID() {
        return ID;
    }
}
