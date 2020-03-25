package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class MinusCommand extends Command {

  public MinusCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return -args.get(0).execute();
  }
}
