package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class HideTurtleCommand extends TurtleCommand {

  public HideTurtleCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    turtle.showTurtle(false);
    return 0;
  }
}
