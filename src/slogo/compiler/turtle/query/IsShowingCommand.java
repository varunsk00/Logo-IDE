package slogo.compiler.turtle.query;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class IsShowingCommand extends TurtleCommand {

  public IsShowingCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    boolean val = turtle.isShowTurtle();
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new IsShowingCommand(declaration);
  }
}
