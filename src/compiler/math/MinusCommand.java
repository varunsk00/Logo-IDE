package compiler.math;

import compiler.Command;

public class MinusCommand extends Command {

  public MinusCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return -args.get(0).execute();
  }

  @Override
  public boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  public Command createCommand(String declaration) {
    return new MinusCommand(declaration);
  }
}
