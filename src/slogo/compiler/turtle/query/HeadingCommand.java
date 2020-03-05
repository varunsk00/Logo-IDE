package slogo.compiler.turtle.query;

import slogo.compiler.parser.Command;

public class HeadingCommand extends TurtleQuery {

  public HeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getHeading();
  }
}
