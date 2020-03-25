package slogo.compiler.turtle.display;

import slogo.compiler.turtle.query.TurtleQuery;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for getting the pen color
 */
public class GetPenColorCommand extends TurtleQuery {

  public GetPenColorCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return turtle.getPenColorIndex();
  }
}
