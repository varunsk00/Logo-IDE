package slogo.compiler.types;

import slogo.compiler.parser.Command;
import slogo.compiler.parser.TypeFactory;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A abstract command for all data structures (numbers, variables, lists, etc)
 */
public abstract class TypeCommand extends Command {

  public TypeCommand(String declaration) {
    super(declaration);
    groupingType = Command.GROUPING_INVALID;
  }

  @Override
  protected void factoryRegister(String className) {
    Command obj = this.createCommand(INITIALIZATION);
    className = className.substring(0, className.length() - 4);
    TypeFactory.registerCommand(className, obj);
  }
}
