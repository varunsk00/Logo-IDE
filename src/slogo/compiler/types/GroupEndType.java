package slogo.compiler.types;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for ending a group
 */
public class GroupEndType extends TypeCommand {

  public GroupEndType(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    System.out.println("A group end was just executed, and that's bad.");
    return 0; //should never happen
  }
}
