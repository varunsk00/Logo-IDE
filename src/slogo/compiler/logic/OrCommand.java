package slogo.compiler.logic;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for or logic
 */
public class OrCommand extends Command {

  public OrCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() != 0 || args.get(1).execute() != 0;
    if (val) {
      return 1;
    }
    return 0;
  }
}
