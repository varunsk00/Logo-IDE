package compiler;

public class MakeVariableCommand extends Command {

  public MakeVariableCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    double value = args.get(1).execute();
    Memory.setVariable(((VariableType) args.get(0)).getName(),
        value); //FIXME refactor args to remove instanceof?
    return value;
  }

  @Override
  boolean isCompleteSub() {
    for (Command c: args) {
      if (!c.isComplete()) {
        return false;
      }
    }
    return args.size()==2 && args.get(0) instanceof VariableType; //FIXME refactor args to remove instanceof?
  }

  @Override
  Command createCommand(String declaration) {
    return new MakeVariableCommand(declaration);
  }
}
