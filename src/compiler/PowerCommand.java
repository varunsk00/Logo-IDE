package compiler;

public class PowerCommand extends Command {

  public PowerCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.pow(args.get(0).execute(), args.get(1).execute());
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==2;
  }

  @Override
  Command createCommand(String declaration) {
    return new PowerCommand(declaration);
  }
}
