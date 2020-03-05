package slogo.compiler.turtle.social;

import java.util.List;
import slogo.compiler.types.ListStartType;

public class AskCommand extends TurtleCreationCommand {

  public AskCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double executeTurtle() {
    List<Integer> actives = parseIDs(args.get(0));
    memory.pushTurtleStack(actives);
    double ret = args.get(1).execute();
    memory.popTurtleStack();
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs &&
        args.get(0) instanceof ListStartType &&
        args.get(1) instanceof ListStartType;
  }
}
