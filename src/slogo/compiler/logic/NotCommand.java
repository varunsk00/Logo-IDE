package slogo.compiler.logic;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for not logic
 */
public class NotCommand extends Command {

  public NotCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
    groupingType = Command.GROUPING_ITERATIVE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() == 0;
    if (val) {
      return 1;
    }
    return 0;
  }
}
