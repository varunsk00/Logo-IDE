package slogo.compiler.exceptions;

public class InvalidTurtleException extends CompilerException {

  public InvalidTurtleException(String msg) {
    super(msg);
  }

  public InvalidTurtleException(Exception e) {
    super(e);
  }
}
