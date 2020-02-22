package compiler.logic;

import compiler.Command;

public class NotCommand extends Command {

  public NotCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute() == 0;
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new NotCommand(declaration);
  }
}
