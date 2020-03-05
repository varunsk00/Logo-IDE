package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class PiCommand extends Command {

  public PiCommand(String declaration) {
    super(declaration);
    desiredArgs = 0;
  }

  @Override
  public double execute() {
    return Math.PI;
  }
}
