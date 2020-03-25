package slogo.compiler.logic;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for and logic
 */

import slogo.compiler.parser.Command;

public class AndCommand extends Command {

  public AndCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() != 0 && args.get(1).execute() != 0;
    if (val) {
      return 1;
    }
    return 0;
  }
}
