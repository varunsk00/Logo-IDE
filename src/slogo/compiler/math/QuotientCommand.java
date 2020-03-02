package slogo.compiler.math;

import slogo.compiler.parser.Command;
import slogo.compiler.exceptions.InvalidArithmeticException;

public class QuotientCommand extends Command {

  public QuotientCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    double denom = args.get(1).execute();
    if (denom == 0) {
      throw new InvalidArithmeticException(errorMsgs.getString("DivideByZero"));
    }
    return args.get(0).execute() / args.get(1).execute();
  }

  @Override
  public Command createCommand(String declaration) {
    return new QuotientCommand(Command.INITIALIZATION);
  }
}
