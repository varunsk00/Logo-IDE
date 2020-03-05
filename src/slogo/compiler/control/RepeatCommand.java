package slogo.compiler.control;

import slogo.compiler.types.ListStartType;

public class RepeatCommand extends LoopCommand {

  public RepeatCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    double val = args.get(0).execute();
    return executeLoop(":repcount", 1, val, 1);
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(1) instanceof ListStartType; //FIXME instanceof
  }
}
