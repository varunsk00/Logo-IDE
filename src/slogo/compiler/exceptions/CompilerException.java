package slogo.compiler.exceptions;

/**
 * @author Maverick Chung mc608
 * <p>
 * Exception to indicate some sort of error in the compiler - syntax error, memory error, arithmetic
 * error, etc.
 */
public class CompilerException extends RuntimeException {

  public CompilerException(String msg) {
    super(msg);
  }

  public CompilerException(Exception e) {
    super(e);
  }

}
