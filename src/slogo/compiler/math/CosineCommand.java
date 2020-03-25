package slogo.compiler.math;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for cosine
 */
public class CosineCommand extends Command {

  public CosineCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.cos(Math.toRadians(args.get(0).execute()));
  }
}
