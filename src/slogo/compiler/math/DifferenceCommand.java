package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class DifferenceCommand extends Command {

  public DifferenceCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    return args.get(0).execute() - args.get(1).execute();
  }

  @Override
  public Command createCommand(String declaration) {
    return new DifferenceCommand(declaration);
  }
}
