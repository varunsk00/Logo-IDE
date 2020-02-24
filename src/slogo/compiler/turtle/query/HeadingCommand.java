package slogo.compiler.turtle.query;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class HeadingCommand extends TurtleCommand {

  public HeadingCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    return turtle.getHeading();
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new HeadingCommand(declaration);
  }
}
