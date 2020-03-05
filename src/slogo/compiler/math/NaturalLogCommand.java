package slogo.compiler.math;

import slogo.compiler.parser.Command;

public class NaturalLogCommand extends Command {

  public NaturalLogCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double executeCommand() {
    return Math.log(args.get(0).execute());
  }
}
