package slogo.compiler.math;

import slogo.compiler.Command;

public class PowerCommand extends Command {

  public PowerCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    return Math.pow(args.get(0).execute(), args.get(1).execute());
  }

  @Override
  public Command createCommand(String declaration) {
    return new PowerCommand(declaration);
  }
}
