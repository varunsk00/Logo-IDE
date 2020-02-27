package slogo.compiler.types;

import slogo.compiler.Command;
import slogo.compiler.TypeFactory;

public abstract class TypeCommand extends Command {

  public TypeCommand(String declaration) {
    super(declaration);
  }

  @Override
  public void factoryRegister(String className) {
    Command obj = this.createCommand(INITIALIZATION);
    className = className.substring(0, className.length() - 4);
    TypeFactory.registerCommand(className, obj);
  }
}
