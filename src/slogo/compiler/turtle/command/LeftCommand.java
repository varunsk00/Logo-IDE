package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class LeftCommand extends TurtleCommand {

  public LeftCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.rotate(-val);
    return val;
  }
}
