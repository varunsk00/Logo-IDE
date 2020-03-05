package slogo.compiler.turtle.query;

public class HeadingCommand extends TurtleQuery {

  public HeadingCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeTurtle() {
    return turtle.getHeading();
  }
}
