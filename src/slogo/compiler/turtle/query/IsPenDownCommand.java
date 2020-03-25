package slogo.compiler.turtle.query;

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
