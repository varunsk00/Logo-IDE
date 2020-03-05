package slogo.compiler.types;

public class VariableType extends TypeCommand {

  public VariableType(String nm) {
    super(nm);
    name = nm;
    desiredArgs = 0;
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
  public String toString() {
    return "var:" + name;
  }

  public String getName() {
    return name;
  }
}
