package slogo.compiler.control;

import java.util.ArrayList;
import slogo.compiler.exceptions.CompilerException;
import slogo.compiler.parser.Command;
import slogo.compiler.types.CommandType;
import slogo.compiler.types.ListStartType;
import slogo.compiler.types.VariableType;

public class MakeUserInstructionCommand extends Command {

  public MakeUserInstructionCommand(String declaration) {
    super(declaration);
    desiredArgs = 3;
  }

  @Override
  public double execute() {
    executed = true;
    try {
      ArrayList<String> vars = new ArrayList<>();
      for (Command var : args.get(1).getArgs()) {
        if (var.typeEquals("variabletype")) {
          vars.add(var.getName());
        }
      }
      String commName = ((CommandType) args.get(0)).getName();
      memory.setUserDefinedCommand(commName, args.get(2));
      memory.setUserDefinedCommandVariables(commName, vars);
    } catch (CompilerException e) {
      return 0;
    }
    return 1;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs &&
        args.get(0).typeEquals("commandtype") &&
        args.get(1).typeEquals("liststart") &&
        args.get(2).typeEquals("liststart");
  }

  /*@Override
  public void addArg(Command c) {
    super.addArg(c);
    if (c instanceof CommandType) {
      ((CommandType) c).setBeingDefined(true);
    }
  }*/
}
