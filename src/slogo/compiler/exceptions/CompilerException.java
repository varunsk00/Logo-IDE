package slogo.compiler.exceptions;

public class CompilerException extends RuntimeException {

  public CompilerException(String msg) {
    super(msg);
  }

  public CompilerException(Exception e) {
    super(e);
  }

}
