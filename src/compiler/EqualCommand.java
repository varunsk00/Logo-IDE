package compiler;

public class EqualCommand extends Command{

  public EqualCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    boolean val = args.get(0).execute() == args.get(1).execute();
    if (val) {
      return 1;
    }
    return 0;
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==2;
  }

  @Override
  Command createCommand(String declaration) {
    return new EqualCommand(declaration);
  }
}
