package slogo.compiler.logic;

import slogo.compiler.parser.Command;

public class NotCommand extends Command {

  public NotCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeCommand() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() == 0;
    if (val) {
      return 1;
    }
    return 0;
  }
}
