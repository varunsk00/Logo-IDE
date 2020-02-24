package slogo.compiler.turtle;

import slogo.compiler.Command;
import slogo.compiler.Memory;
import slogo.turtle.Turtle;

public abstract class TurtleCommand extends Command {

  protected Turtle turtle;

  public TurtleCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    turtle = Memory.getCurrentTurtle();
    return executeTurtle();
  }

  public abstract double executeTurtle();

  //FIXME register might do a bad thing by registering the abstract TurtleCommand class?
}
