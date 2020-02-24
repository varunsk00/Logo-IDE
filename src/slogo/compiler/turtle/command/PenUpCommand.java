package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class PenUpCommand extends TurtleCommand {

  public PenUpCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    turtle.setPenDown(false);
    return 0;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new PenUpCommand(declaration);
  }
}
