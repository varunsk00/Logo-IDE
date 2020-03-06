package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

public class SetShapeCommand extends TurtleCommand {

  public SetShapeCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    int ret = (int) args.get(0).execute();
    turtle.setShapeIndex(ret);
    return ret;
  }
}
