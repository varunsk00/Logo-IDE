package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class SetHeadingCommand extends TurtleCommand {

  public SetHeadingCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double executeTurtle() {
    double currentHead = turtle.getHeading();
    double val = args.get(0).execute();
    turtle.setHeading(val);
    return val-currentHead;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new SetHeadingCommand(declaration);
  }
}
