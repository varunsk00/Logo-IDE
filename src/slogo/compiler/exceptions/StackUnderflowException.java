package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class StackUnderflowException extends CompilerException {

  public StackUnderflowException(String msg) {
    super(msg);
  }

  public StackUnderflowException(Exception e) {
    super(e);
  }
}
