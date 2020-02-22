package compiler;

public class ArcTangentCommand extends Command {

  public ArcTangentCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.toDegrees(Math.atan(args.get(0).execute()));
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new ArcTangentCommand(declaration);
  }
}
