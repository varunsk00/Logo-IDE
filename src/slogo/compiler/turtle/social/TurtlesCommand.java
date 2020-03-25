package slogo.compiler.turtle.social;

import slogo.compiler.turtle.query.TurtleQuery;

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
