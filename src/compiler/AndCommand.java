package compiler;

public class AndCommand extends Command {

  public AndCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() { //FIXME add double tolerance (e.g. cos 90 != 0)
    boolean val = args.get(0).execute()!=0 && args.get(1).execute() != 0;
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  Command createCommand(String declaration) {
    return new AndCommand(declaration);
  }
}
