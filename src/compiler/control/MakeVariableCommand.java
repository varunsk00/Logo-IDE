package compiler.control;

import compiler.Command;
import compiler.Memory;
import compiler.types.VariableType;

public class MakeVariableCommand extends Command {

  public MakeVariableCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    double value = args.get(1).execute();
    Memory.setVariable(((VariableType) args.get(0)).getName(),
        value); //FIXME refactor args to remove instanceof?
    return value;
  }

  @Override
  public boolean isCompleteSub() {
    for (Command c : args) {
      if (!c.isComplete()) {
        return false;
      }
    }
    return args.size() == 2 && args
        .get(0) instanceof VariableType; //FIXME refactor args to remove instanceof?
  }

  @Override
  public Command createCommand(String declaration) {
    return new MakeVariableCommand(declaration);
  }
}
