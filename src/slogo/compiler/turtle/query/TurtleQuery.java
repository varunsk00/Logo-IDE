package slogo.compiler.turtle.query;

import slogo.compiler.turtle.TurtleCommand;

public abstract class TurtleQuery extends TurtleCommand {

  public TurtleQuery(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    turtle = memory.getCurrentTurtle();
    return executeTurtle();
  }

}
