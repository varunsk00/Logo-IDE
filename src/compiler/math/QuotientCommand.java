package compiler.math;

import compiler.Command;
import compiler.exceptions.InvalidArithmeticException;

public class QuotientCommand extends Command {

  public QuotientCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    double denom = args.get(1).execute();
    if (denom==0) {
      throw new InvalidArithmeticException("Attempted to divide by 0");
    }
    return args.get(0).execute()/args.get(1).execute();
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  public Command createCommand(String declaration) {
    return new QuotientCommand(Command.INITIALIZATION);
  }
}
