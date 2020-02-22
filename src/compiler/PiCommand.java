package compiler;

public class PiCommand extends Command {

  public PiCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    return Math.PI;
  }

  @Override
  boolean isCompleteSub() {
    return true;
  }

  @Override
  Command createCommand(String declaration) {
    return new PiCommand(declaration);
  }
}
