package compiler;

import java.util.Random;

public class RandomCommand extends Command {

  public RandomCommand(String declaration) {
    super(declaration);
  }

  @Override
  double execute() {
    Random rand = new Random();
    return rand.nextDouble()*args.get(0).execute();
  }

  @Override
  boolean isCompleteSub() {
    return args.size()==1;
  }

  @Override
  Command createCommand(String declaration) {
    return new RandomCommand(declaration);
  }
}
