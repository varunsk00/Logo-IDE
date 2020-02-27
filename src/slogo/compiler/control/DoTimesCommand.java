package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.types.ListStartType;
import slogo.compiler.types.VariableType;

public class DoTimesCommand extends LoopCommand {

  public DoTimesCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    double val = args.get(0).getArgs().get(1).execute(); //FIXME magic val
    String varName = ((VariableType) args.get(0).getArgs().get(0))
        .getName(); //FIXME bad bad bad maybe do a tostring?
    double ret = executeLoop(varName,1, val,1);
    return ret;
  }

  @Override
  public boolean isCompleteSub() { //FIXME instanceofs everywhere
    if (!(args.size() == 2 && args.get(0) instanceof ListStartType && args
        .get(1) instanceof ListStartType)) {
      return false; //has 2 args, both lists
    }
    return args.get(0).getArgs().size() == 3 && args.get(0).getArgs()
        .get(0) instanceof VariableType; //list has two args, 1st is variable
  }

  @Override
  public Command createCommand(String declaration) {
    return new DoTimesCommand(declaration);
  }
}
