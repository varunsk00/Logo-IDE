package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

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
