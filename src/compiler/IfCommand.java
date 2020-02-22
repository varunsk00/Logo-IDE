package compiler;

public class IfCommand extends Command {

  public IfCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    double val = args.get(0).execute();
    double ret = 0;
    if (val != 0) {
      ret = args.get(1).execute();
    }
    return ret;
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==2 && args.get(1) instanceof ListStartType; //FIXME instanceof
  }

  @Override
  Command createCommand(String declaration) {
    return new IfCommand(declaration);
  }
}
