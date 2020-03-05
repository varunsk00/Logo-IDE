package slogo.compiler.control;

import slogo.compiler.parser.Command;

public abstract class LoopCommand extends Command {
  public static final double DOUBLE_TOLERANCE = .000000001;

  public LoopCommand(String declaration) {
    super(declaration);
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
