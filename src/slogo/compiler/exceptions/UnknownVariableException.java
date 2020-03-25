package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class UnknownVariableException extends CompilerException {

  public UnknownVariableException(String msg) {
    super(msg);
  }

  public UnknownVariableException(Exception e) {
    super(e);
  }
}
