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
  private boolean isActive = true;
  private int ID;
  private int shapeIndex;
  private int penColorIndex;
  private double penSize;

  private List<Point> locations;

  public Turtle() {
    xLocation = CENTER_X;
    yLocation = CENTER_Y;
    heading = NORTH;
    penDown = true;
    showTurtle = true;
    locations = new ArrayList<>();
    ID = -1;
    shapeIndex = 1;
    penColorIndex = 1;
  }

  public Turtle(Turtle other) {
    xLocation = other.xLocation;
    yLocation = other.yLocation;
    heading = other.heading;
    penDown = other.penDown;
    showTurtle = other.showTurtle;
    ID = other.ID;
    shapeIndex = other.shapeIndex;
    penColorIndex = other.penColorIndex;
    isActive = other.isActive;
    oldPenDown = other.oldPenDown;
    clearScreen = other.clearScreen;
    penSize = other.penSize;

    locations = new ArrayList<>();
    for (Point p : other.locations) {
      locations.add(new Point(p));
    }
  }

  public Turtle(int id) {
    this();
    ID = id;
  }

  public void move(double pixel) {
    xLocation = Math.sin(Math.toRadians(heading)) * pixel + xLocation;
    yLocation = -Math.cos(Math.toRadians(heading)) * pixel + yLocation;
    Point p = new Point(xLocation, yLocation);
    p.setDrawn(penDown);
    locations.add(p);
  }

  public List<Point> locationsList() {
    List<Point> ret = new ArrayList<>(locations);
    locations.clear();
    return ret;
  }

  public void rotate(double degree) {
    setHeading(heading + degree);
  }

  public void towards(double x, double y) {
    setHeading((Math.atan2(x - xLocation, -(y - yLocation)) * RAD_TO_DEG_RATIO));
  }

  public void showTurtle(boolean showTurtle) {
    this.showTurtle = showTurtle;
  }

  public void goHome() {
    xLocation = CENTER_X;
    yLocation = CENTER_Y;
    locations.add(new Point(xLocation, yLocation));
  }

  public double getXLocation() {
    return xLocation;
  }

  public void setXLocation(double xLocation) {
    this.xLocation = xLocation;
  }

  public double getYLocation() {
    return yLocation;
  }

  public void setYLocation(double yLocation) {
    this.yLocation = yLocation;
  }

  public boolean isPenDown() {
    return penDown;
  }

  public void setPenDown(boolean penDown) {
    this.penDown = penDown;
  }

  public double getHeading() {
    return heading;
  }

  public void setHeading(double degree) {
    heading = degree % 360;
  }

  public boolean isShowTurtle() {
    return showTurtle;
  }

  public boolean isCleared() {
    return clearScreen;
  }

  public void setCleared(boolean clear) {
    clearScreen = clear;
    if (clear) {
      oldPenDown = penDown;
      setPenDown(false);
      resetLocation();
    }
  }

  public void resetLocation() {
    locations.add(new Point(xLocation, yLocation));
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

  public int getShapeIndex() {
    return shapeIndex;
  }

  public void setShapeIndex(int shapeIndex) {
    this.shapeIndex = shapeIndex;
  }

  public int getPenColorIndex() {
    return penColorIndex;
  }

  public void setPenColorIndex(int colorIndex) {
    this.penColorIndex = colorIndex;
  }

  public double getPenSize() {
    return penSize;
  }

  public void setPenSize(double psize) {
    penSize = psize;
  }
}
