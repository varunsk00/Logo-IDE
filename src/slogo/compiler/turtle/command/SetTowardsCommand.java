package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for setting heading toward a point
 */
public class SetTowardsCommand extends TurtleCommand {

  public SetTowardsCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double currentHead = turtle.getHeading();
    double x = args.get(0).execute();
    double y = args.get(1).execute();
    turtle.towards(x, y);
    return turtle.getHeading() - currentHead;
  }
}
