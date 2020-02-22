package compiler.exceptions;

public class InvalidSyntaxException extends CompilerException {

  public InvalidSyntaxException(String msg) {
    super(msg);
  }

  public InvalidSyntaxException(Exception e) {
    super(e);
  }
}
