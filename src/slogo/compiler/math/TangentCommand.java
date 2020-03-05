package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class TangentCommand extends Command {

  public TangentCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double execute() {
    return Math.tan(Math.toRadians(args.get(0).execute()));
  }
}
