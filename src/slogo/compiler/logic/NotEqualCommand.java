package slogo.compiler.logic;

import slogo.compiler.Command;

public class NotEqualCommand extends Command {

  public NotEqualCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    boolean val = args.get(0).execute() != args.get(1).execute();
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  public Command createCommand(String declaration) {
    return new NotEqualCommand(declaration);
  }
}
