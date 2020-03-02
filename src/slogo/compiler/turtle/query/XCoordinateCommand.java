package slogo.compiler.turtle.query;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class XCoordinateCommand extends TurtleCommand {

  public XCoordinateCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getXLocation();
  }

  @Override
  public Command createCommand(String declaration) {
    return new XCoordinateCommand(declaration);
  }
}
