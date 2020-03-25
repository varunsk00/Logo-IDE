package slogo.compiler.turtle.social;

import java.util.List;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for executing a command on some turtles
 */
public class AskCommand extends TurtleCreationCommand {

  public AskCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeTurtle() {
    List<Integer> actives = parseIDs(args.get(0));
    memory.pushTurtleStack(actives);
    double ret = args.get(1).execute();
    memory.popTurtleStack();
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs &&
        args.get(0).typeEquals("liststart") &&
        args.get(1).typeEquals("liststart");
  }
}
