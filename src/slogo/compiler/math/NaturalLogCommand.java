package slogo.compiler.math;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for natural log
 */
public class NaturalLogCommand extends Command {

  public NaturalLogCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.log(args.get(0).execute());
  }
}
