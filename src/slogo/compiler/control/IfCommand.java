package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.types.ListStartType;

public class IfCommand extends Command {

  public IfCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    double val = args.get(0).execute();
    double ret = 0;
    if (val != 0) {
      ret = args.get(1).execute();
    }
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(1) instanceof ListStartType; //FIXME instanceof
  }

  @Override
  public Command createCommand(String declaration) {
    return new IfCommand(declaration);
  }
}
