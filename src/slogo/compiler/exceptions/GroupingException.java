package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class GroupingException extends CompilerException {

  public GroupingException(String msg) {
    super(msg);
  }

  public GroupingException(Exception e) {
    super(e);
  }
}
