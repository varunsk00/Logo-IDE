package slogo.compiler.control;

import slogo.compiler.parser.Command;

public class MakeVariableCommand extends Command {

  public MakeVariableCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    double value = args.get(1).execute();
    memory.setVariable(args.get(0).getName(), value);
    return value;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == desiredArgs && args.get(0).typeEquals("variabletype");
  }
}
