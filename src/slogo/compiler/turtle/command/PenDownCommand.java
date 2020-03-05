package slogo.compiler.turtle.command;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.TurtleCommand;

public class PenDownCommand extends TurtleCommand {

  public PenDownCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    turtle.setPenDown(true);
    return 1;
  }
}
