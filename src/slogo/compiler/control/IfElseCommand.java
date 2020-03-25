package slogo.compiler.control;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for an if/else statement
 */
public class IfElseCommand extends Command {

  public IfElseCommand(String declaration) {
    super(declaration);
    desiredArgs = 3;
    groupingType = Command.GROUPING_ITERATIVE;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double val = args.get(0).execute();
    double ret = 0;
    if (val != 0) {
      ret = args.get(1).execute();
    } else {
      ret = args.get(2).execute();
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs &&
        args.get(1).typeEquals("liststart") &&
        args.get(2).typeEquals("liststart");
  }
}
