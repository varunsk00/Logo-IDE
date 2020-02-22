package compiler;

public class ConstantType extends Command {

  private double value;

  public ConstantType(String val) {
    super(val);
    if (val.equals(Command.INITIALIZATION)) {
      return;
    }
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
  boolean isCompleteSub() {
    return true;
  }

  @Override
  public String toString(){
    return "const:"+value;
  }

  @Override
  Command createCommand(String declaration) {
    return new ConstantType(declaration);
  }
}
