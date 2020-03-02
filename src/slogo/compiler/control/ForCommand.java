package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.types.ListStartType;
import slogo.compiler.types.VariableType;

public class ForCommand extends LoopCommand {

  public ForCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    String varName = ((VariableType) args.get(0).getArgs().get(0))
        .getName(); //FIXME bad bad bad maybe do a tostring?
    double start = args.get(0).getArgs().get(1).execute();
    double end = args.get(0).getArgs().get(2).execute();
    double inc = args.get(0).getArgs().get(3).execute();
    return executeLoop(varName, start, end, inc);
  }

  @Override
  public boolean isCompleteSub() { //FIXME instanceofs everywhere
    return args.size() == 2 &&
        args.get(0) instanceof ListStartType &&
        args.get(1) instanceof ListStartType &&
        args.get(0).getArgs().size() == 5 &&
        args.get(0).getArgs().get(0) instanceof VariableType; //list has 4 args, 1st is variable
  }

  @Override
  public Command createCommand(String declaration) {
    return new ForCommand(declaration);
  }
}
