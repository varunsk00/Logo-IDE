package compiler.math;

import compiler.Command;

public class TangentCommand extends Command {

  public TangentCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return Math.tan(Math.toRadians(args.get(0).execute()));
  }

  @Override
  public boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new TangentCommand(declaration);
  }
}
