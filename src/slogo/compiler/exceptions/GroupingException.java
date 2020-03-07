package slogo.compiler.exceptions;

public class GroupingException extends CompilerException {

  public GroupingException(String msg) {
    super(msg);
  }

  public GroupingException(Exception e) {
    super(e);
  }
}
