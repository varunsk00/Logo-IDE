package slogo.compiler.turtle.query;

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
