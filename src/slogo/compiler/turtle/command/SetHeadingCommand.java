package slogo.compiler.turtle.command;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.TurtleCommand;

public class SetHeadingCommand extends TurtleCommand {

  public SetHeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    double currentHead = turtle.getHeading();
    double val = args.get(0).execute();
    turtle.setHeading(val);
    return val - currentHead;
  }

  @Override
  public Command createCommand(String declaration) {
    return new SetHeadingCommand(declaration);
  }
}
