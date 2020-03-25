package slogo.compiler.turtle.query;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for getting whether the turtle is shown
 */
public class IsShowingCommand extends TurtleQuery {

  public IsShowingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    boolean val = turtle.isShowTurtle();
    if (val) {
      return 1;
    }
    return 0;
  }
}
