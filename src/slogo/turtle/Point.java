package slogo.turtle;

/**
 * @author Maverick Chung mc608
 * <p>
 * Just a simple class to hold a 2-dimensional point. Used for drawing purposes
 */
public class Point {

  private double x;
  private double y;
  private boolean drawn;

  public Point(double x, double y) {
    this.x = x;
    this.y = y;
  }

  public Point(Point other) {
    x = other.x;
    y = other.y;
  }

  public double getX() {
    return x;
  }

  public double getY() {
    return y;
  }

  public boolean getDrawn() {
    return drawn;
  }

  public void setDrawn(boolean d) {
    drawn = d;
  }

}
