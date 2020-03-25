package slogo.compiler.turtle.query;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for getting the pen state of a turtle
 */
public class IsPenDownCommand extends TurtleQuery {

  public IsPenDownCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    boolean val = turtle.isPenDown();
    if (val) {
      return 1;
    }
    return 0;
  }
}
