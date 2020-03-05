package slogo.compiler.turtle.social;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.query.TurtleQuery;

public class IDCommand extends TurtleQuery {

  public IDCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    return 0;
  }
}
