package slogo.compiler.types;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for starting a list, in which all included commands are executed sequentially
 */
public class ListStartType extends TypeCommand {

  public ListStartType(String declaration) {
    super(declaration);
    groupingType = Command.GROUPING_INVALID;
    desiredArgs = -1; //should never be used
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double ret = 0;
    for (int i = 0; i < args.size() - 1; i++) {
      Command c = args.get(i);
      ret = c.execute();
    }
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() > 0 && args.get(args.size() - 1).typeEquals("listend");
  }
}
