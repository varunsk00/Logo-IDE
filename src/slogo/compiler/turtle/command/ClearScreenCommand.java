package slogo.compiler.turtle.command;

import slogo.compiler.turtle.query.TurtleQuery;
import slogo.turtle.Turtle;

public class ClearScreenCommand extends TurtleQuery {

  public ClearScreenCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    double x = 0;
    double y = 0;
    for (int i: memory.getAllTurtleIDs()) {
      Turtle t = memory.getTurtleByID(i);
      x = t.getXLocation();
      y = t.getYLocation();
      t.setCleared(true);
      t.goHome();
      t.setHeading(0);
    }
    return Math.pow(x * x + y * y, .5);
  }
}
