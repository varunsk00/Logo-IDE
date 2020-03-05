package slogo.compiler.logic;

import slogo.compiler.parser.Command;

public class GreaterThanCommand extends Command {

  public GreaterThanCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double executeCommand() {
    boolean val = args.get(0).execute() > args.get(1).execute();
    if (val) {
      return 1;
    }
    return 0;
  }
}
