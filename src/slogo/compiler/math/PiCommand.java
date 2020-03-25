package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class PiCommand extends Command {

  public PiCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.PI;
  }
}
