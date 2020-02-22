package compiler.math;

import compiler.Command;

public class ProductCommand extends Command {

  public ProductCommand(String declaration) {
    super(declaration);
  }

  @Override
  public double execute() {
    return args.get(0).execute()*args.get(1).execute();
  }

  @Override
  public boolean isCompleteSub() {
    return args.size() == 2;
  }

  @Override
  public Command createCommand(String declaration) {
    return new ProductCommand(declaration);
  }
}
