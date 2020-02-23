package slogo.compiler.types;

import slogo.compiler.Command;
import slogo.compiler.Memory;

public class CommandType extends Command {

  private String name;

  public CommandType(String declaration) {
    super(declaration);
    name = declaration;
  }

  @Override
  public double execute() {
    return Memory.getUserDefinedCommand(name).execute();
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new CommandType(declaration);
  }

  public String getName () {
    return name;
  }
}
