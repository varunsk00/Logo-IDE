package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class SineCommand extends Command {

  public SineCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeCommand() {
    return Math.sin(Math.toRadians(args.get(0).execute()));
  }
}
