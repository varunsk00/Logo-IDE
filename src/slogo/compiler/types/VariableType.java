package slogo.compiler.types;

import slogo.compiler.Command;
import slogo.compiler.Memory;

public class VariableType extends TypeCommand {

  private String name;

  public VariableType(String nm) {
    super(nm);
    name = nm;
  }

  @Override
  public double execute() {
    return memory.getVariable(name);
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
