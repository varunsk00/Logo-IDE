package slogo.compiler.turtle.social;

import slogo.compiler.turtle.query.TurtleQuery;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for returning the number of turtles
 */
public class TurtlesCommand extends TurtleQuery {

  public TurtlesCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    return memory.getAllTurtleIDs().size();
  }
}
