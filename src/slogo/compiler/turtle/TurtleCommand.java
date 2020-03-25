package slogo.compiler.turtle;

import java.util.List;
import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;


/**
 * @author Maverick Chung mc608
 *
 * Purpose: A abstract command for commands that interact with turtles
 */
public abstract class TurtleCommand extends Command {

  protected Turtle turtle;

  public TurtleCommand(String declaration) {
    super(declaration);
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    List<Integer> actives = memory.getActiveTurtleIDs();
    double ret = 0;
    for (int i : actives) {
      memory.setCurrentTurtle(i);
      turtle = memory.getCurrentTurtle();
      ret = executeTurtle();
    }
    return ret;
  }

  /**
   * Executes the command on the current active turtles
   * @return the return value of the last executed command
   */
  protected abstract double executeTurtle();

}
