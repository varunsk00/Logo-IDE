package slogo.compiler.math;

import slogo.compiler.Command;

public class ProductCommand extends Command {

  public ProductCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
  }

  @Override
  public double execute() {
    return args.get(0).execute() * args.get(1).execute();
  }

  @Override
  public Command createCommand(String declaration) {
    return new ProductCommand(declaration);
  }
}
