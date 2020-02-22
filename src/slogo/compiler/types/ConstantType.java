package slogo.compiler.types;

import slogo.compiler.Command;

public class ConstantType extends Command {

  private double value;

  public ConstantType(String val) {
    super(val);
    if (val.equals(Command.INITIALIZATION)) {
      return;
    }
    try {
      value = Double.parseDouble(val);
    } catch (NumberFormatException e) {
      System.out.println("bad number given: " + val); //FIXME bandaid
    }
  }

  @Override
  public double execute() {
    return value;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public String toString() {
    return "const:" + value;
  }

  @Override
  public Command createCommand(String declaration) {
    return new ConstantType(declaration);
  }
}
