package compiler;

public class MinusCommand extends Command {

  public MinusCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return -args.get(0).execute();
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new MinusCommand(declaration);
  }
}
