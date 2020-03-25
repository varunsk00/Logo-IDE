package slogo.compiler.turtle.social;

import slogo.compiler.turtle.query.TurtleQuery;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for getting the ID of a turtle
 */
public class IDCommand extends TurtleQuery {

  public IDCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return memory.getCurrentTurtleID();
  }
}
