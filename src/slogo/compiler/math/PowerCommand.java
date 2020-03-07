package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class PowerCommand extends Command {

  public PowerCommand(String declaration) {
    super(declaration);
    desiredArgs = 2;
    groupingType = Command.GROUPING_RECURSIVE;
  }

  @Override
  public double executeCommand() {
    return Math.pow(args.get(0).execute(), args.get(1).execute());
  }
}
