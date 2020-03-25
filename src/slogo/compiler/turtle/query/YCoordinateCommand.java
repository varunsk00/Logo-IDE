package slogo.compiler.turtle.query;

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
