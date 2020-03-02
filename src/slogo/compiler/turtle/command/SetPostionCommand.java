package slogo.compiler.turtle.command;

import slogo.compiler.Command;
import slogo.compiler.turtle.TurtleCommand;

public class SetPostionCommand extends TurtleCommand {

  public SetPostionCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double executeTurtle() {
    double currx = turtle.getXLocation();
    double curry = turtle.getYLocation();
    double x = args.get(0).execute();
    double y = args.get(1).execute();
    turtle.setXLocation(x);
    turtle.setYLocation(y);
    double dx = x - currx;
    double dy = y - curry;
    return Math.pow(dx * dx + dy * dy, .5);
  }

  @Override
  public Command createCommand(String declaration) {
    return new SetPostionCommand(declaration);
  }
}
