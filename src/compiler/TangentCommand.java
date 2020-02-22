package compiler;

public class TangentCommand extends Command {

  public TangentCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.tan(Math.toRadians(args.get(0).execute()));
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new TangentCommand(declaration);
  }
}
