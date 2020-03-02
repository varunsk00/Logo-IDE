package slogo.compiler.turtle.command;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.TurtleCommand;

public class LeftCommand extends TurtleCommand {

  public LeftCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.rotate(-val);
    return val;
  }

  @Override
  public Command createCommand(String declaration) {
    return new LeftCommand(declaration);
  }
}
