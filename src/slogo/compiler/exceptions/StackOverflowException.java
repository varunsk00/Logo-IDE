package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class StackOverflowException extends CompilerException {

  public StackOverflowException(String msg) {
    super(msg);
  }

  public StackOverflowException(Exception e) {
    super(e);
  }
}
