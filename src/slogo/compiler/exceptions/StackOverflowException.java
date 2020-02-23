package slogo.compiler.exceptions;

public class StackOverflowException extends CompilerException {

  public StackOverflowException(String msg) {
    super(msg);
  }

  public StackOverflowException(Exception e) {
    super(e);
  }
}
