package slogo.compiler.control;

import slogo.compiler.parser.Command;


/**
 * @author Maverick Chung mc608
 *
 * Purpose: An abstract command for implementing all loops
 */
public abstract class LoopCommand extends Command {

  public static final double DOUBLE_TOLERANCE = .000000001; //such that the final value is included in loops

  public LoopCommand(String declaration) {
    super(declaration);
    groupingType = Command.GROUPING_ITERATIVE;
  }

  protected double executeLoop(String varName, double start, double end, double inc) {
    double ret = 0;
    end += DOUBLE_TOLERANCE;
    for (double i = start; i <= end; i += inc) {
      memory.setVariable(varName, i);
      ret = args.get(1).execute();
    }
    return ret;
  }
}
