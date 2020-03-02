package slogo.compiler.types;

import slogo.compiler.parser.Command;

public class ListEndType extends TypeCommand {

  public ListEndType(String declaration) {
    super(declaration);
    desiredArgs = 0;
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
