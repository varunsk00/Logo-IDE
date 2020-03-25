package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class InvalidSyntaxException extends CompilerException {

  public InvalidSyntaxException(String msg) {
    super(msg);
  }

  public InvalidSyntaxException(Exception e) {
    super(e);
  }
}
