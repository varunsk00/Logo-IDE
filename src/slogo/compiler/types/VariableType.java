package slogo.compiler.types;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for variables, which store a numeric value
 */
public class VariableType extends TypeCommand {

  public VariableType(String nm) {
    super(nm);
    name = nm;
    desiredArgs = 0;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return memory.getVariable(name);
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public String toString() {
    return "var:" + name;
  }
}
