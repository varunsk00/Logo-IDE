package slogo.compiler.logic;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for less than comparison
 */
public class LessThanCommand extends Command {

  public LessThanCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_COMPARISON;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    boolean val = args.get(0).execute() < args.get(1).execute();
    if (val) {
      return 1;
    }
    return 0;
  }

}
