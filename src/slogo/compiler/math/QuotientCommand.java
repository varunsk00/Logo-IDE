package slogo.compiler.math;

import slogo.compiler.exceptions.InvalidArithmeticException;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for division
 */
public class QuotientCommand extends Command {

  public QuotientCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double denom = args.get(1).execute();
    if (denom == 0) {
      throw new InvalidArithmeticException(errorMsgs.getString("DivideByZero"));
    }
    return args.get(0).execute() / args.get(1).execute();
  }
}
