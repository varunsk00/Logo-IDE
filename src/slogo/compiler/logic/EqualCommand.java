package slogo.compiler.logic;

import slogo.compiler.parser.Command;

public class EqualCommand extends Command {

  public EqualCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    boolean val = args.get(0).execute() == args.get(1).execute();
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  public Command createCommand(String declaration) {
    return new EqualCommand(declaration);
  }
}
