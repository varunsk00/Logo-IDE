package slogo.compiler.control;

import java.util.ArrayList;
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
      ArrayList<String> vars = new ArrayList<>();
      for (Command var: args.get(1).getArgs()) {
        if (var instanceof VariableType) {
          vars.add(((VariableType) var).getName()); //FIXME oh my god you monster
        }
      }
      String commName = ((CommandType) args.get(0)).getName();
      Memory.setUserDefinedCommand(commName, args.get(2));
      Memory.setUserDefinedCommandVariables(commName, vars);
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

  /*@Override
  public void addArg(Command c) {
    super.addArg(c);
    if (c instanceof CommandType) {
      ((CommandType) c).setBeingDefined(true);
    }
  }*/
}
