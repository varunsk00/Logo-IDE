package slogo.compiler.turtle.query;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: An abstract command for asking for turtle information without affecting the turtle
 */
public abstract class TurtleQuery extends TurtleCommand {

  public TurtleQuery(String declaration) {
    super(declaration);
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    turtle = memory.getCurrentTurtle();
    return executeTurtle();
  }

}
