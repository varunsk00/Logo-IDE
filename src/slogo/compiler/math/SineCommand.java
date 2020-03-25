package slogo.compiler.math;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for sine
 */
public class SineCommand extends Command {

  public SineCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.sin(Math.toRadians(args.get(0).execute()));
  }
}
