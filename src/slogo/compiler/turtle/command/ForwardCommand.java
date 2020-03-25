package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class ForwardCommand extends TurtleCommand {

  public ForwardCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.move(val);
    return val;
  }
}
