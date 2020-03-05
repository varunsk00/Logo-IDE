package slogo.compiler.turtle.query;

import slogo.compiler.parser.Command;

public class YCoordinateCommand extends TurtleQuery {

  public YCoordinateCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getYLocation();
  }
}
