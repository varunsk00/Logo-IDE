package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.types.ListStartType;

public class IfElseCommand extends Command {

  public IfElseCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    double val = args.get(0).execute();
    double ret = 0;
    if (val != 0) {
      ret = args.get(1).execute();
    } else {
      ret = args.get(2).execute();
    }
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 3 && args.get(1) instanceof ListStartType && args
        .get(2) instanceof ListStartType; //FIXME instanceof
  }

  @Override
  public Command createCommand(String declaration) {
    return new IfElseCommand(declaration);
  }
}
