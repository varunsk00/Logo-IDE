package compiler;

public class RepeatCommand extends Command {

  public RepeatCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    double val = args.get(0).execute();
    double ret = 0;
    for (int i = 1; i <= val+.0000000001; i++) { //FIXME magic val
      Memory.setVariable(":repcount", i); //FIXME un-hardcode String?
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
    return new RepeatCommand(declaration);
  }
}
