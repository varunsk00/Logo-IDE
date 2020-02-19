package compiler;

import java.util.ArrayList;

abstract class Command {

  public int desiredArgs;
  protected ArrayList<Command> args;

  public Command(){
    args = new ArrayList<>();
  }

  abstract double execute();

  abstract boolean isComplete();

  public void addArg(Command arg) {
    args.add(arg);
  }

  public void addArg(Command arg, int n) {
    while(args.size()<n) {
      args.add(null);
    }
    args.set(n, arg);
  }


}
