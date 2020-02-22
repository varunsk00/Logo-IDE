package compiler;

public class DifferenceCommand extends Command {

  public DifferenceCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return args.get(0).execute()-args.get(1).execute();
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==2;
  }

  @Override
  Command createCommand(String declaration) {
    return new DifferenceCommand(declaration);
  }
}
