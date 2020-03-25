package slogo.compiler.turtle.command;

import slogo.compiler.turtle.TurtleCommand;

public class HomeCommand extends TurtleCommand {

  public HomeCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double x = turtle.getXLocation();
    double y = turtle.getYLocation();
    turtle.goHome();
    turtle.setHeading(0);
    return Math.pow(x * x + y * y, 0.5);
  }
}
