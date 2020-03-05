package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

public class SetPenColorCommand extends TurtleCommand {

  public SetPenColorCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    int ret = (int) args.get(0).execute();
    turtle.setPenColorIndex(ret);
    return ret;
  }
}
