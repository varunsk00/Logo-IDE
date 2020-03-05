package slogo.compiler.turtle.display;

import slogo.compiler.turtle.query.TurtleQuery;

public class GetPenColorCommand extends TurtleQuery {

  public GetPenColorCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getPenColorIndex();
  }
}
