package slogo.compiler.turtle.query;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class YCoordinateCommand extends TurtleCommand {

  public YCoordinateCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    return turtle.getYLocation();
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new YCoordinateCommand(declaration);
  }
}
