package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class CosineCommand extends Command {

  public CosineCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeCommand() {
    return Math.cos(Math.toRadians(args.get(0).execute()));
  }
}
