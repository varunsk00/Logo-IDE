package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class InvalidArithmeticException extends CompilerException {

  public InvalidArithmeticException(String msg) {
    super(msg);
  }

  public InvalidArithmeticException(Exception e) {
    super(e);
  }
}
