package slogo.compiler.turtle.social;

import java.util.ArrayList;
import java.util.List;
import slogo.compiler.parser.Command;
import slogo.compiler.turtle.query.TurtleQuery;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A abstract command for all commands that execute on multiple turtles (tell, ask, askwith)
 */
public abstract class TurtleCreationCommand extends TurtleQuery {

  public TurtleCreationCommand(String declaration) {
    super(declaration);
  }

  protected List<Integer> parseIDs(Command c) {
    ArrayList<Integer> actives = new ArrayList<>();
    List<Command> listargs = c.getArgs();
    for (int i = 0; i < listargs.size() - 1; i++) {
      actives.add((int) listargs.get(i).execute());
    }
    return actives;
  }
}
