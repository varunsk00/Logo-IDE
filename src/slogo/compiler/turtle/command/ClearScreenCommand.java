package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class ClearScreenCommand extends TurtleCommand {

  public ClearScreenCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    double x = turtle.getXLocation();
    double y = turtle.getYLocation();
    turtle.setCleared(true);
    turtle.goHome();
    turtle.setHeading(0);
    return Math.pow(x * x + y * y, .5);
  }

  @Override
  public Command createCommand(String declaration) {
    return new ClearScreenCommand(declaration);
  }
}
