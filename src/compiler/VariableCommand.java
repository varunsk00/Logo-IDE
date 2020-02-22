package compiler;

public class VariableCommand extends Command {

  private String name;

  public VariableCommand(String nm) {
    super(nm);
    name = nm;
  }

  @Override
  double execute() {
    return Memory.getVariable(name);
  }

  @Override
  void register() {
    TypeFactory.registerCommand("Variable", new VariableCommand(Command.INITIALIZATION));
  }

  @Override
  boolean isComplete() {
    return true;
  }

  @Override
  Command createCommand(String declaration) {
    return new VariableCommand(declaration);
  }

  protected String getName() {
    return name;
  }
}
