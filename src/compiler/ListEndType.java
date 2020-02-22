package compiler;

public class ListEndType extends Command {

  public ListEndType(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    System.out.println("A list end was just executed, and that's bad.");
    return 0; //should never happen
  }

  @Override
  boolean isCompleteSub() {
    return true;
  }

  @Override
  Command createCommand(String declaration) {
    return new ListEndType(declaration);
  }
}
