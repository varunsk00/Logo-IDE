package slogo.compiler.types;

import java.util.List;
import slogo.compiler.Command;
import slogo.compiler.Memory;

public class CommandType extends Command {

  private String name;
  private List<String> variables;
  private boolean beingDefined = false; //fixme

  public CommandType(String declaration) {
    super(declaration);
    if (declaration == Command.INITIALIZATION) {
      return;
    }
    name = declaration;
    variables = Memory.getCommandVariables(name);
  }

  @Override
  public double execute() {
    for (int i = 0; i < variables.size(); i++) {
      Memory.setVariable(variables.get(i), args.get(i).execute());
      //System.out.println(variables.get(i));
    }
    //recPrint();
    //Command exe = Memory.getUserDefinedCommand(name);
    //exe.recPrint();
    return Memory.getUserDefinedCommand(name).execute();
  }

  @Override
  public boolean isCompleteSub() {
    return beingDefined || args.size() == variables.size();
  }

  @Override
  public Command createCommand(String declaration) {
    return new CommandType(declaration);
  }

  public String getName () {
    return name;
  }

  public void setBeingDefined(boolean def) {
    beingDefined = def; //fixme
  }
}
