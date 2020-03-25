package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 */
public class InvalidTurtleException extends CompilerException {

  public InvalidTurtleException(String msg) {
    super(msg);
  }

  public InvalidTurtleException(Exception e) {
    super(e);
  }
}
