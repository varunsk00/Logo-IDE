package slogo.compiler.types;

import slogo.compiler.Command;
import slogo.compiler.Memory;

public class VariableType extends Command {

  private String name;

  public VariableType(String nm) {
    super(nm);
    name = nm;
  }

  @Override
  public double execute() {
    return Memory.getVariable(name);
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new VariableType(declaration);
  }

  @Override
  public String toString() {
    return "var:" + name;
  }

  public String getName() {
    return name;
  }
}
