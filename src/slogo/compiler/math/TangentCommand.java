package slogo.compiler.math;

import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: A command for tangent
 */
public class TangentCommand extends Command {

  public TangentCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  /**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    return Math.tan(Math.toRadians(args.get(0).execute()));
  }
}
