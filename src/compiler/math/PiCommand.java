package compiler.math;

import compiler.Command;

public class PiCommand extends Command {

  public PiCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return Math.PI;
  }

  @Override
  public boolean isCompleteSub() {
    return true;
  }

  @Override
  public Command createCommand(String declaration) {
    return new PiCommand(declaration);
  }
}
