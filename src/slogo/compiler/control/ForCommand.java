package slogo.compiler.control;

import slogo.compiler.types.ListStartType;
import slogo.compiler.types.VariableType;

public class ForCommand extends LoopCommand {

  public ForCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    String varName = args.get(0).getArgs().get(0).getName();
    double start = args.get(0).getArgs().get(1).execute();
    double end = args.get(0).getArgs().get(2).execute();
    double inc = args.get(0).getArgs().get(3).execute();
    return executeLoop(varName, start, end, inc);
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 2 &&
        args.get(0).typeEquals("liststart") &&
        args.get(1).typeEquals("liststart") &&
        args.get(0).getArgs().size() == 5 &&
        args.get(0).getArgs().get(0).typeEquals("variabletype"); //list has 4 args, 1st is variable
  }
}
