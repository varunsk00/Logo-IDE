package slogo.compiler.turtle.query;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for getting the x coordinate of the turtle
 */
public class XCoordinateCommand extends TurtleQuery {

  public XCoordinateCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return turtle.getXLocation();
  }
}
