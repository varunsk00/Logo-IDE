package slogo.compiler.turtle.query;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.TurtleCommand;

public class YCoordinateCommand extends TurtleCommand {

  public YCoordinateCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getYLocation();
  }

  @Override
  public Command createCommand(String declaration) {
    return new YCoordinateCommand(declaration);
  }
}
