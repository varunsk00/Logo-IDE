package slogo.compiler.turtle.social;

import java.util.List;

public class TellCommand extends TurtleCreationCommand {

  public TellCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    List<Integer> actives = parseIDs(args.get(0));
    memory.tellTurtleStack(actives);
    if (actives.isEmpty()) {
      return 0;
    }
    return actives.get(actives.size() - 1);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(0).typeEquals("liststart");
  }
}
