package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class HomeCommand extends TurtleCommand {

  public HomeCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    double x = turtle.getXLocation();
    double y = turtle.getYLocation();
    turtle.goHome();
    return Math.pow(x * x + y * y, 0.5);
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new HomeCommand(declaration);
  }
}
