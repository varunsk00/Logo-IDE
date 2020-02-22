package compiler;

import java.util.ArrayList;

abstract class Command {

  protected static final String INITIALIZATION = "this is an initialization string that should never happen";

  public int desiredArgs;
  protected ArrayList<Command> args;

  public Command(String declaration) {
    if (declaration.equals(INITIALIZATION)) {
      return;
    }
    register();
    args = new ArrayList<>();
  }

  abstract double execute();

  abstract void register();

  abstract boolean isComplete();

  abstract Command createCommand(String declaration);

  public void addArg(Command arg) {
    args.add(arg);
  }

  public void addArg(Command arg, int n) {
    while (args.size() < n) {
      args.add(null);
    }
    args.set(n, arg);
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder(this.getClass().getName() + " ");
    for (Command i: args) {
      ret.append(i.toString()).append(" ");
    }
    return ret.toString();
  }


}
