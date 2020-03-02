package slogo.compiler.math;

import slogo.compiler.Command;

public class ArcTangentCommand extends Command {

  public ArcTangentCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double execute() {
    return Math.toDegrees(Math.atan(args.get(0).execute()));
  }

  @Override
  public Command createCommand(String declaration) {
    return new ArcTangentCommand(declaration);
  }
}
