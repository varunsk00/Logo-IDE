package compiler;

public class SineCommand extends Command {

  public SineCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.sin(Math.toRadians(args.get(0).execute()));
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new SineCommand(declaration);
  }
}
