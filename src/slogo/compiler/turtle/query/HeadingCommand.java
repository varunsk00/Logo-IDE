package slogo.compiler.turtle.query;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class HeadingCommand extends TurtleCommand {

  public HeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getHeading();
  }

  @Override
  public Command createCommand(String declaration) {
    return new HeadingCommand(declaration);
  }
}
