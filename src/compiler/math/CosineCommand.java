package compiler.math;

import compiler.Command;

public class CosineCommand extends Command {

  public CosineCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return Math.cos(Math.toRadians(args.get(0).execute()));
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new CosineCommand(declaration);
  }
}
