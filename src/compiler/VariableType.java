package compiler;

public class VariableType extends Command {

  private String name;

  public VariableType(String nm) {
    super(nm);
    name = nm;
  }

  @Override
  double execute() {
    return Memory.getVariable(name);
  }

  @Override
  boolean isCompleteSub() {
    return true;
  }

  @Override
  Command createCommand(String declaration) {
    return new VariableType(declaration);
  }

  @Override
  public String toString(){
    return "var:"+name;
  }

  public String getName() {
    return name;
  }
}
