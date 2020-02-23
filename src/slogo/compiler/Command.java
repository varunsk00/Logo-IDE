package slogo.compiler;

import java.util.ArrayList;
import java.util.List;

public abstract class Command {

  protected static final String INITIALIZATION = "this is an initialization string that should never happen";

  public int desiredArgs;
  protected ArrayList<Command> args;

  public Command(String declaration) {
    args = new ArrayList<>();
    if (declaration.equals(INITIALIZATION)) {
      return;
    }
    register();
  }

  public abstract double execute();

  public void register() {
    Command obj = this.createCommand(INITIALIZATION);
    String className = this.getClass().getName();
    String[] classNameArr = className.split("\\.");
    className = classNameArr[classNameArr.length - 1];
    String classType = className.substring(className.length() - 4);
    if (classType.equals("Type")) {
      className = className.substring(0, className.length() - 4);
      TypeFactory.registerCommand(className, obj);

    } else if (classType.equals("mand")) {
      className = className.substring(0, className.length() - 7);
      CommandFactory.registerCommand(className, obj);
    } else {
      throw new RuntimeException("bad class name");
    }
  }

  public abstract boolean isCompleteSub();

  public abstract Command createCommand(String declaration);

  public boolean isComplete() {
    for (Command c : args) {
      if (!c.isComplete()) {
        return false;
      }
    }
    return isCompleteSub();
  }

  public void addArg(Command arg) {
    args.add(arg);
  }

  public void addArg(Command arg, int n) {
    while (args.size() < n) {
      args.add(null);
    }
    args.set(n, arg);
  }

  public List<Command> getArgs() {
    return args;
  }

  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder(this.getClass().getName() + " ");
    /*for (Command i : args) {
      ret.append(i.toString()).append(" ");
    }*/
    return ret.toString();
  }

  public void recPrint() {
    System.out.print(""+this+" ");
    for (Command c: args) {
      System.out.print(""+c+" ");
    }
    for (Command c: args) {
      c.recPrint();
    }
    System.out.println();

  }


}
