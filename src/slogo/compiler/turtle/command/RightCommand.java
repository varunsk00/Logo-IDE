package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for rotating right
 */
public class RightCommand extends TurtleCommand {

  public RightCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.rotate(val);
    return val;
  }
}
