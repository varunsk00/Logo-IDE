package slogo.compiler.turtle.social;

import java.util.ArrayList;
import java.util.List;
import slogo.compiler.parser.Command;
import slogo.compiler.turtle.query.TurtleQuery;
import slogo.compiler.types.ListStartType;

public class TellCommand extends TurtleQuery {

  public TellCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeTurtle() {
    double ret = 0;
    ArrayList<Integer> actives = new ArrayList<>();
    List<Command> listargs = args.get(0).getArgs();
    for (int i = 0; i < listargs.size()-1; i++) {
      ret = (int) listargs.get(i).execute();
      actives.add((int) ret);
    }
    memory.tellTurtleStack(actives);
    return ret;
  }

  @Override
  public boolean isCompleteSub(){
    return args.size() == desiredArgs && args.get(0) instanceof ListStartType;
  }
}
