package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class MinusCommand extends Command {

  public MinusCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double execute() {
    return -args.get(0).execute();
  }
}
