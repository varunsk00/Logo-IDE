package slogo.compiler.turtle.display;

import slogo.compiler.turtle.query.TurtleQuery;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for getting the shape of a turtle
 */
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
