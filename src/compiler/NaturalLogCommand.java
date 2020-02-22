package compiler;

public class NaturalLogCommand extends Command {

  public NaturalLogCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.log(args.get(0).execute());
  }

  @Override
  boolean isCompleteSub() {
    return args.size() == 1;
  }

  @Override
  Command createCommand(String declaration) {
    return new NaturalLogCommand(declaration);
  }
}
