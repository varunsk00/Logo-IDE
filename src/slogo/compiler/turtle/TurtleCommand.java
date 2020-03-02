package slogo.compiler.turtle;

import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;

public abstract class TurtleCommand extends Command {

  protected Turtle turtle;

  public TurtleCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    turtle = memory.getCurrentTurtle();
    return executeTurtle();
  }

  public abstract double executeTurtle();

}
