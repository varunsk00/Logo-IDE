package slogo.compiler.types;

public class GroupEndType extends TypeCommand{

  public GroupEndType(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double executeCommand() {
    System.out.println("A group end was just executed, and that's bad.");
    return 0; //should never happen
  }
}
