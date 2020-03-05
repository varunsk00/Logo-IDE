package slogo.compiler.turtle.query;

import slogo.compiler.parser.Command;

public class IsShowingCommand extends TurtleQuery {

  public IsShowingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    boolean val = turtle.isShowTurtle();
    if (val) {
      return 1;
    }
    return 0;
  }
}
