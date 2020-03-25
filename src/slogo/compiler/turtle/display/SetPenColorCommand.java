package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for setting the pen color of a turtle
 */
public class SetPenColorCommand extends TurtleCommand {

  public SetPenColorCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    int ret = (int) args.get(0).execute();
    turtle.setPenColorIndex(ret);
    memory.setPenColorIndex(ret);
    return ret;
  }
}
