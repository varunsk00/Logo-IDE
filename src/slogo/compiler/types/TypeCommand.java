package slogo.compiler.types;

import slogo.compiler.Command;

public abstract class TypeCommand extends Command {

  public TypeCommand(String declaration) {
    super(declaration);
  }
}
