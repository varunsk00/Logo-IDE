package slogo.compiler.exceptions;

public class UnknownVariableException extends CompilerException {

  public UnknownVariableException(String msg) {
    super(msg);
  }

  public UnknownVariableException(Exception e) {
    super(e);
  }
}
