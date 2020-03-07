package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class ProductCommand extends Command {

  public ProductCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

  @Override
  public double executeCommand() {
    return args.get(0).execute() * args.get(1).execute();
  }
}
