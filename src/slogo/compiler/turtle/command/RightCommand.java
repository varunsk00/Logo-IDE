package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class RightCommand extends TurtleCommand {

  public RightCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    double val = args.get(0).execute();
    turtle.rotate(val);
    return val;
  }

  @Override
  public Command createCommand(String declaration) {
    return new RightCommand(declaration);
  }
}
