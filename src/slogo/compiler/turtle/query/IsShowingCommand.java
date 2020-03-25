package slogo.compiler.turtle.query;

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
