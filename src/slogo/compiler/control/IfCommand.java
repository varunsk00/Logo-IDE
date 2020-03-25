package slogo.compiler.control;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for an if statement
 */
import slogo.compiler.parser.Command;

public class IfCommand extends Command {

  public IfCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
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
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(1).typeEquals("liststart");
  }
}
