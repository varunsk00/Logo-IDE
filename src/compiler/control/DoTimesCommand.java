package compiler.control;

import compiler.Command;
import compiler.types.ListStartType;
import compiler.Memory;
import compiler.types.VariableType;

public class DoTimesCommand extends Command {

  public DoTimesCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    double ret = 0;
    double val = args.get(0).getArgs().get(1).execute() + .0000000001; //FIXME magic val
    String varName = ((VariableType) args.get(0).getArgs().get(0)).getName(); //FIXME bad bad bad maybe do a tostring?
    for (int i = 1; i < val; i++) {
      Memory.setVariable(varName, i);
      ret = args.get(1).execute();
    }
    return ret;
  }

  @Override
  public boolean isCompleteSub() { //FIXME instanceofs everywhere
    if (!(args.size() == 2 && args.get(0) instanceof ListStartType && args
        .get(1) instanceof ListStartType)) {
      return false; //has 2 args, both lists
    }
    return args.get(0).getArgs().size()==3 && args.get(0).getArgs().get(0) instanceof VariableType; //list has two args, 1st is variable
  }

  @Override
  public Command createCommand(String declaration) {
    return new DoTimesCommand(declaration);
  }
}
