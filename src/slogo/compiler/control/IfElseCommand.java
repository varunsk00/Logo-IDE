package slogo.compiler.control;

import slogo.compiler.parser.Command;
import slogo.compiler.types.ListStartType;

public class IfElseCommand extends Command {

  public IfElseCommand(String declaration) {
    super(declaration);
    desiredArgs = 3;
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
    return args.size() == desiredArgs &&
        args.get(1).typeEquals("liststart") &&
        args.get(2).typeEquals("liststart");
  }
}
