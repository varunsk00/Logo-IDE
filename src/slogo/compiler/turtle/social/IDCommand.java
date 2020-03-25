package slogo.compiler.turtle.social;

import slogo.compiler.turtle.query.TurtleQuery;

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
