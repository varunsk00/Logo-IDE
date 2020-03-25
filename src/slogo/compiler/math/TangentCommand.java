package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class TangentCommand extends Command {

  public TangentCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.tan(Math.toRadians(args.get(0).execute()));
  }
}
