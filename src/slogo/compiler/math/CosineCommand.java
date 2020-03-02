package slogo.compiler.math;

import slogo.compiler.Command;

public class CosineCommand extends Command {

  public CosineCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double execute() {
    return Math.cos(Math.toRadians(args.get(0).execute()));
  }

  @Override
  public Command createCommand(String declaration) {
    return new CosineCommand(declaration);
  }
}
