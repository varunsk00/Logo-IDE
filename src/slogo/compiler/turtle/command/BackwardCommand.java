package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class BackwardCommand extends TurtleCommand {

  public BackwardCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.move(-val);
    return val;
  }
}
