package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for setting heading
 */
public class SetHeadingCommand extends TurtleCommand {

  public SetHeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double currentHead = turtle.getHeading();
    double val = args.get(0).execute();
    turtle.setHeading(val);
    return val - currentHead;
  }
}
