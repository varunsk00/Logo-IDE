package compiler;

public class ConstantCommand extends Command
{

  private double value;
  public ConstantCommand(String val) {
    super(val);
    value = Double.parseDouble(val);
  }
  @Override
  double execute() {
    return value;
  }

  @Override
  void register() {
    TypeFactory fact = new TypeFactory();
    fact.registerCommand("Command", new ConstantCommand(Command.INITIALIZATION));
  }

  @Override
  boolean isComplete() {
    return true;
  }

  @Override
  Command createCommand(String declaration) {
    return new ConstantCommand(declaration);
  }
}
