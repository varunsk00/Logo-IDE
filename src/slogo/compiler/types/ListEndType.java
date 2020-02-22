package slogo.compiler.types;

import slogo.compiler.Command;

public class ListEndType extends Command {

  public ListEndType(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    System.out.println("A list end was just executed, and that's bad.");
    return 0; //should never happen
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new ListEndType(declaration);
  }
}
