package slogo.compiler.types;

import slogo.compiler.Command;

public class ListStartType extends TypeCommand {

  public ListStartType(String declaration) {
    super(declaration);
    desiredArgs = -1; //should never be used
  }

  @Override
  public double execute() {
    double ret = 0;
    for (int i = 0; i < args.size() - 1; i++) {
      Command c = args.get(i);
      ret = c.execute();
    }
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() > 0 && args.get(args.size() - 1) instanceof ListEndType; //FIXME instanceof
  }

  @Override
  public Command createCommand(String declaration) {
    return new ListStartType(declaration);
  }
}
