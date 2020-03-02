package slogo.compiler.logic;

import slogo.compiler.parser.Command;

public class OrCommand extends Command {

  public OrCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() != 0 || args.get(1).execute() != 0;
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  public Command createCommand(String declaration) {
    return new OrCommand(declaration);
  }
}
