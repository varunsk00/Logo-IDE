package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

public class SetPenSizeCommand extends TurtleCommand {

  public SetPenSizeCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    double ret = args.get(0).execute();
    turtle.setPenSize(ret);
    return ret;
  }
}
