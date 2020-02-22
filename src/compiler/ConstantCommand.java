package compiler;

public class ConstantCommand extends Command {

  private double value;

  public ConstantCommand(String val) {
    super(val);
    try {
      value = Double.parseDouble(val);
    } catch (NumberFormatException e) {
      System.out.println("bad number given: "+val); //FIXME bandaid
    }
  }

  @Override
  double execute() {
    return value;
  }

  @Override
  void register() {
    TypeFactory.registerCommand("Constant", new ConstantCommand(Command.INITIALIZATION));
  }

  @Override
  boolean isComplete() {
    return true;
  }

  @Override
  public String toString(){
    return "const:"+value;
  }

  @Override
  Command createCommand(String declaration) {
    return new ConstantCommand(declaration);
  }
}
