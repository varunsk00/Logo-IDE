package slogo.compiler.math;

import java.util.Random;
import slogo.compiler.parser.Command;

public class RandomCommand extends Command {

  public RandomCommand(String declaration) {
    super(declaration);
    desiredArgs = 1;
  }

  @Override
  public double execute() {
    Random rand = new Random();
    return rand.nextDouble() * args.get(0).execute();
  }
}
