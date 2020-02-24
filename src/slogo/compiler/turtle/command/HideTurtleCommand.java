package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class HideTurtleCommand extends TurtleCommand {

  public HideTurtleCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    turtle.showTurtle(false);
    return 0;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new HideTurtleCommand(declaration);
  }
}
