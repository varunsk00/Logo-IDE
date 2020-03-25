package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class PenUpCommand extends TurtleCommand {

  public PenUpCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    turtle.setPenDown(false);
    return 0;
  }
}
