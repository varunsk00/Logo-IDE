package slogo.compiler;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.compiler.control.MakeUserInstructionCommand;

public abstract class Command {

  protected static final String INITIALIZATION = "this is an initialization string that should never happen";

  protected Memory memory;
  protected ArrayList<Command> args;
  protected ResourceBundle errorMsgs;
  protected boolean executed = false; //FIXME only used by makeuserinstruction, can't be trusted

  public Command(String declaration) {
    args = new ArrayList<>();
    if (declaration.equals(INITIALIZATION)) {
      return;
    }
    register();
  }

  public abstract double execute();

  public void register() {
    String className = findClass();
    factoryRegister(className);
  }

  private String findClass() {
    String className = this.getClass().getName();
    String[] classNameArr = className.split("\\.");
    className = classNameArr[classNameArr.length - 1];
    return className;
  }

  protected void factoryRegister(String className) {
    Command obj = this.createCommand(INITIALIZATION);
    className = className.substring(0, className.length() - 7);
    CommandFactory.registerCommand(className, obj);
  }

  public void setMemory(Memory mem) {
    memory = mem;
    for (Command c : args) {
      c.setMemory(mem);
    }
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
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
    System.out.print("" + this + " ");
    for (Command c : args) {
      System.out.print("" + c + " ");
    }
    for (Command c : args) {
      c.recPrint();
    }
    System.out.println();

  }

  public boolean containsDefinition() {
    if (this instanceof MakeUserInstructionCommand) { //fixme bad bad bad
      return true;
    }
    for (Command c : args) {
      if (c.containsDefinition()) {
        return true;
      }
    }
    return false;
  }

  public int countDefinitions() {
    int ret = 0;
    if (this instanceof MakeUserInstructionCommand) { //fixme bad bad bad
      ret++;
    }
    for (Command c: args) {
      ret += c.countDefinitions();
    }
    return ret;
  }

  public Command findFirstDef() {
    if (this instanceof MakeUserInstructionCommand && !executed) { //fixme bad bad bad
      return this;
    }
    for (Command c : args) {
      Command ret = c.findFirstDef();
      if (ret != null && !ret.executed) {
        return ret;
      }
    }
    return null;
  }


}
