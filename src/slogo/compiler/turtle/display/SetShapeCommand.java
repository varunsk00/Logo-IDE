package slogo.compiler.turtle.display;

import slogo.compiler.turtle.TurtleCommand;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for setting the shape of a turtle
 */
public class SetShapeCommand extends TurtleCommand {

  public SetShapeCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    int ret = (int) args.get(0).execute();
    turtle.setShapeIndex(ret);
    memory.setShapeIndex(ret);
    return ret;
  }
}
