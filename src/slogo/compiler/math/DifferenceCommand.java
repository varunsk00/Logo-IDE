package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class DifferenceCommand extends Command {

  public DifferenceCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return args.get(0).execute() - args.get(1).execute();
  }
}
