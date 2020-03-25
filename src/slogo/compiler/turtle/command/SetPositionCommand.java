package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for setting position
 */
public class SetPositionCommand extends TurtleCommand {

  public SetPositionCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double currx = turtle.getXLocation();
    double curry = turtle.getYLocation();
    double x = args.get(0).execute();
    double y = args.get(1).execute();
    turtle.setXLocation(x);
    turtle.setYLocation(y);
    double dx = x - currx;
    double dy = y - curry;
    return Math.pow(dx * dx + dy * dy, .5);
  }
}
