package slogo.compiler.math;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for exponentiation
 */
public class PowerCommand extends Command {

  public PowerCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.pow(args.get(0).execute(), args.get(1).execute());
  }
}
