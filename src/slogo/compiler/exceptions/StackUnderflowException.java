package slogo.compiler.exceptions;

public class StackUnderflowException extends CompilerException {

  public StackUnderflowException(String msg) {
    super(msg);
  }

  public StackUnderflowException(Exception e) {
    super(e);
  }
}
