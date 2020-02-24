package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class PenDownCommand extends TurtleCommand {

  public PenDownCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    turtle.setPenDown(true);
    return 1;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new PenDownCommand(declaration);
  }
}
