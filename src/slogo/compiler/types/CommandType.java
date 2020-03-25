package slogo.compiler.types;

import java.util.List;
import slogo.compiler.exceptions.InvalidSyntaxException;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for user defined commands
 */
public class CommandType extends TypeCommand {

  private List<String> variables;

  public CommandType(String declaration) {
    super(declaration);
    if (declaration.equals(Command.INITIALIZATION)) {
      return;
    }
    name = declaration;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    memory.pushMemoryStack();
    for (int i = 0; i < variables.size(); i++) {
      memory.setVariable(variables.get(i), args.get(i).execute());
      //System.out.println(variables.get(i));
    }
    //recPrint();
    //Command exe = memory.getUserDefinedCommand(name);
    //exe.recPrint();
    double ret = 0.0;
    try {
      ret = memory.getUserDefinedCommand(name).execute();
    } catch (NullPointerException e) {
      memory.popMemoryStack();
      throw new InvalidSyntaxException(
          String.format(errorMsgs.getString("UserCommandNotFound"), name));
    }
    memory.popMemoryStack();
    return ret;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    variables = memory.getCommandVariables(name);
    desiredArgs = variables.size();
    return args.size() == desiredArgs;
  }
}
