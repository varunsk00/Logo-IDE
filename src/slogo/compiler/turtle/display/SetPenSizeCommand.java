package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for setting the pen width of a turtle
 */
public class SetPenSizeCommand extends TurtleCommand {

  public SetPenSizeCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    double ret = args.get(0).execute();
    turtle.setPenSize(ret);
    return ret;
  }
}
