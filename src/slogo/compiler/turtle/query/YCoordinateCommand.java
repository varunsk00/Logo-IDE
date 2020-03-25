package slogo.compiler.turtle.query;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for getting the y coordinate of the turtle
 */
public class YCoordinateCommand extends TurtleQuery {

  public YCoordinateCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return turtle.getYLocation();
  }
}
