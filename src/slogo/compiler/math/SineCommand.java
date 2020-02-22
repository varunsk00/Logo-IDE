package slogo.compiler.math;

import slogo.compiler.Command;

public class SineCommand extends Command {

  public SineCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return Math.sin(Math.toRadians(args.get(0).execute()));
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new SineCommand(declaration);
  }
}
