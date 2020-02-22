package slogo.compiler.exceptions;

public class StackOverflowException extends CompilerException {

  public static final int MAX_RECURSION_DEPTH = 1000;

  public StackOverflowException(String msg) {
    super(msg);
  }

  public StackOverflowException(Exception e) {
    super(e);
  }
}
