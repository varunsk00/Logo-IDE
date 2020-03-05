package slogo.compiler.turtle;

import java.util.List;
import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;

public abstract class TurtleCommand extends Command {

  protected Turtle turtle;

  public TurtleCommand(String declaration) {
    super(declaration);
  }

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

  protected abstract double executeTurtle();

}
