package slogo.compiler.turtle.display;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for setting the background index
 */
public class SetBackgroundCommand extends Command {

  public SetBackgroundCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    int ret = (int) args.get(0).execute();
    memory.setBackgroundColor(ret);
    return ret;
  }
}
