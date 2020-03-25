package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for showing a turtle
 */
public class ShowTurtleCommand extends TurtleCommand {

  public ShowTurtleCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    turtle.showTurtle(true);
    return 1;
  }
}
