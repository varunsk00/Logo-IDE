package slogo.compiler.math;

import java.util.Random;
import slogo.compiler.Command;

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

  @Override
  public Command createCommand(String declaration) {
    return new RandomCommand(declaration);
  }
}
