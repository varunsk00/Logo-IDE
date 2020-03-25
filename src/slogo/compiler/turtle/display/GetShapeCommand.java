package slogo.compiler.turtle.display;

import slogo.compiler.turtle.query.TurtleQuery;

public class GetShapeCommand extends TurtleQuery {

  public GetShapeCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return turtle.getShapeIndex();
  }
}
