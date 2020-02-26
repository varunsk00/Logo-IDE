package slogo.compiler.types;

import java.util.List;
import slogo.compiler.Command;
import slogo.compiler.Memory;

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
    variables = memory.getCommandVariables(name);
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
    double ret = memory.getUserDefinedCommand(name).execute();
    memory.popMemoryStack();
    return ret;
  }

  @Override
  public boolean isCompleteSub() {
    return beingDefined || args.size() == variables.size();
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
