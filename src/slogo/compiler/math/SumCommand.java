package slogo.compiler.math;

import slogo.compiler.Command;

public class SumCommand extends Command {

  public SumCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    return args.get(0).execute() + args.get(1).execute();
  }

  @Override
  public Command createCommand(String declaration) {
    return new SumCommand(declaration);
  }
}
