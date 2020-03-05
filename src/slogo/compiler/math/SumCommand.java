package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class SumCommand extends Command {

  public SumCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    return args.get(0).execute() + args.get(1).execute();
  }
}
