package compiler.control;

import compiler.Command;
import compiler.types.ListStartType;

public class IfCommand extends Command {

  public IfCommand(String declaration) {
    super(declaration);
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
    return args.size()==2 && args.get(1) instanceof ListStartType; //FIXME instanceof
  }

  @Override
  public Command createCommand(String declaration) {
    return new IfCommand(declaration);
  }
}
