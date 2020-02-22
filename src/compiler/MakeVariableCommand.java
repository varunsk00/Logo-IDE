package compiler;

public class MakeVariableCommand extends Command {

  public MakeVariableCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    double value = args.get(1).execute();
    Memory.setVariable(((VariableCommand) args.get(0)).getName(),
        value); //FIXME refactor args to remove instanceof?
    return value;
  }

  @Override
  void register() {
    CommandFactory.registerCommand("MakeVariable", new MakeVariableCommand(Command.INITIALIZATION));
  }

  @Override
  boolean isComplete() {
    return args.get(0) instanceof VariableCommand && args.size() == 2;
  }

  @Override
  Command createCommand(String declaration) {
    return new MakeVariableCommand(declaration);
  }
}
