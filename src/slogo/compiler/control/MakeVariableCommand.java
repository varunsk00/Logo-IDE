package slogo.compiler.control;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for defining variables
 */
public class MakeVariableCommand extends Command {

  public MakeVariableCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    double value = args.get(1).execute();
    memory.setVariable(args.get(0).getName(), value);
    return value;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(0).typeEquals("variabletype");
  }
}
