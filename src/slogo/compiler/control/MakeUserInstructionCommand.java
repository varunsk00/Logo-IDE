package slogo.compiler.control;

import slogo.compiler.Command;
import slogo.compiler.Memory;
import slogo.compiler.exceptions.CompilerException;
import slogo.compiler.types.CommandType;
import slogo.compiler.types.ListStartType;
import slogo.compiler.types.VariableType;

public class MakeUserInstructionCommand extends Command {

  public MakeUserInstructionCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    try {
      for (Command var: args.get(1).getArgs()) {
        if (var instanceof VariableType) {
          Memory.putIfAbsent(((VariableType) var).getName()); //FIXME oh my god you monster
        }
      }
      Memory.setUserDefinedCommand(((CommandType) args.get(0)).getName(), args.get(2));
    } catch (CompilerException e) {
      return 0;
    }
    return 1;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 3 &&
        args.get(0) instanceof CommandType &&
        args.get(1) instanceof ListStartType &&
        args.get(2) instanceof ListStartType;
  }

  @Override
  public Command createCommand(String declaration) {
    return new MakeUserInstructionCommand(declaration);
  }
}
