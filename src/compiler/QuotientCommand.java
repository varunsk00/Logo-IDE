package compiler;

import compiler.exceptions.InvalidArithmeticException;

public class QuotientCommand extends Command {

  public QuotientCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    double denom = args.get(1).execute();
    if (denom==0) {
      throw new InvalidArithmeticException("Attempted to divide by 0");
    }
    return args.get(0).execute()/args.get(1).execute();
  }

  @Override
  boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  Command createCommand(String declaration) {
    return new QuotientCommand(Command.INITIALIZATION);
  }
}
