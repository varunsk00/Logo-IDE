package slogo.compiler.types;

import java.util.List;
import slogo.compiler.Command;
import slogo.compiler.exceptions.InvalidSyntaxException;

public class CommandType extends TypeCommand {

  private String name;
  private List<String> variables;
  private boolean beingDefined = false; //fixme

  public CommandType(String declaration) {
    super(declaration);
    if (declaration.equals(Command.INITIALIZATION)) {
      return;
    }
    name = declaration;
  }

  @Override
  public double execute() {
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
      throw new InvalidSyntaxException("User command (" + name + ") not defined.");
    }
    memory.popMemoryStack();
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    variables = memory.getCommandVariables(name);
    return beingDefined || (args.size() == variables.size());
  }

  @Override
  public Command createCommand(String declaration) {
    return new CommandType(declaration);
  }

  public String getName() {
    return name;
  }

  public void setBeingDefined(boolean def) {
    beingDefined = def; //fixme
  }
}
