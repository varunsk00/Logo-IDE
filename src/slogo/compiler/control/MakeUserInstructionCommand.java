package slogo.compiler.control;

import java.util.ArrayList;
import slogo.compiler.exceptions.CompilerException;
import slogo.compiler.exceptions.GroupingException;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for creating user defined commands.
 */
public class MakeUserInstructionCommand extends Command {

  public MakeUserInstructionCommand(String declaration) {
    super(declaration);
    desiredArgs = 3;
    groupingType = Command.GROUPING_INVALID;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    try {
      ArrayList<String> vars = new ArrayList<>();
      for (Command var : args.get(1).getArgs()) {
        if (var.typeEquals("variabletype")) {
          vars.add(var.getName());
        }
      }
      String commName = args.get(0).getName();
      memory.setUserDefinedCommand(commName, args.get(2));
      memory.setUserDefinedCommandVariables(commName, vars);
    } catch (CompilerException e) {
      System.out.println("Compiler Exception in MakeUserCommand:" + e.toString());
      return 0;
    } catch (IndexOutOfBoundsException e) {
      throw new GroupingException(errorMsgs.getString("GroupTo"));
    }
    return 1;
  }

  /**
   * {@inheritDoc}
   */
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
