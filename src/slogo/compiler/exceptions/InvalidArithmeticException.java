package slogo.compiler.exceptions;

public class InvalidArithmeticException extends CompilerException {

  public InvalidArithmeticException(String msg) {
    super(msg);
  }

  public InvalidArithmeticException(Exception e) {
    super(e);
  }
}
