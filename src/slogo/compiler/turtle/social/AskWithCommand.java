package slogo.compiler.turtle.social;

import java.util.ArrayList;
import java.util.List;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for executing a command on turtles that meet a criteria
 */
public class AskWithCommand extends AskCommand {

  public AskWithCommand(String declaration) {
    super(declaration);
  }

  @Override
  protected List<Integer> parseIDs(Command c) {
    ArrayList<Integer> actives = new ArrayList<>();
    for (int i : memory.getAllTurtleIDs()) {
      memory.setCurrentTurtle(i);
      int ret = (int) c.execute();
      if (ret != 0) {
        actives.add(i);
      }
    }
    return actives;
  }
}
