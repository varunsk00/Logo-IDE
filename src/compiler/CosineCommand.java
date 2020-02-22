package compiler;

public class CosineCommand extends Command {

  public CosineCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.cos(Math.toRadians(args.get(0).execute()));
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new CosineCommand(declaration);
  }
}
