package slogo.compiler.turtle.command;

import slogo.compiler.parser.Command;
import slogo.compiler.turtle.TurtleCommand;

public class SetTowardsCommand extends TurtleCommand {

  public SetTowardsCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double executeTurtle() {
    double currentHead = turtle.getHeading();
    double x = args.get(0).execute();
    double y = args.get(1).execute();
    turtle.towards(x, y);
    return turtle.getHeading() - currentHead;
  }
}
