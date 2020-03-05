package slogo.compiler.turtle.query;

import slogo.compiler.parser.Command;

public class IsPenDownCommand extends TurtleQuery {

  public IsPenDownCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    boolean val = turtle.isPenDown();
    if (val) {
      return 1;
    }
    return 0;
  }
}
