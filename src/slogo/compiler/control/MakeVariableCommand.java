package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.Memory;
import slogo.compiler.types.VariableType;

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
    return args.size() == 2 && args
        .get(0) instanceof VariableType; //FIXME refactor args to remove instanceof?
  }

  @Override
  public Command createCommand(String declaration) {
    return new MakeVariableCommand(declaration);
  }
}
