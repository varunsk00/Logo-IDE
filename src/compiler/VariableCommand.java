package compiler;

public class VariableCommand extends Command {

  private String name;

  public VariableCommand(String nm) {
    super();
    name = nm;
  }

  @Override
  double execute() {
    return Memory.getVariable(name);
  }

  @Override
  boolean isComplete() {
    return true;
  }

  protected String getName() {
    return name;
  }
}
