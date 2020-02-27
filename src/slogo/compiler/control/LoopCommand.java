package slogo.compiler.control;

import slogo.compiler.Command;

public abstract class LoopCommand extends Command {

  public LoopCommand(String declaration) {
    super(declaration);
  }

  protected double executeLoop(String varName, double start, double end, double inc) {
    double ret = 0;
    end += .000000001; //FIXME magic val
    for (double i = start; i <= end; i += inc) {
      memory.setVariable(varName, i);
      ret = args.get(1).execute();
    }
    return ret;
  }
}
