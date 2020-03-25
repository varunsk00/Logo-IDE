package slogo.compiler.turtle.query;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for getting the heading of a turtle
 */
public class HeadingCommand extends TurtleQuery {

  public HeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return turtle.getHeading();
  }
}
