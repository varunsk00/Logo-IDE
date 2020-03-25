package slogo.compiler.math;

import java.util.Random;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: A command for RNG
 */
public class RandomCommand extends Command {

  public RandomCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

/**
   * {@inheritDoc}
   */
  @Override
  public double executeCommand() {
    Random rand = new Random();
    return rand.nextDouble() * args.get(0).execute();
  }
}
