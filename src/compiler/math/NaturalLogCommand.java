package compiler.math;

import compiler.Command;

public class NaturalLogCommand extends Command {

  public NaturalLogCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return Math.log(args.get(0).execute());
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new NaturalLogCommand(declaration);
  }
}
