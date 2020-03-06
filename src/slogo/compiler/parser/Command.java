package slogo.compiler.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.compiler.parser.memory.Memory;

public abstract class Command {

  protected static final String INITIALIZATION = "this is an initialization string that should never happen";

  protected Memory memory;
  protected ArrayList<Command> args;
  protected ResourceBundle errorMsgs;
  protected boolean executed = false;
  protected int desiredArgs;
  protected String name;
  protected String type;
  private boolean isComplete;

  public Command(String declaration) {
    args = new ArrayList<>();
    if (declaration.equals(INITIALIZATION)) {
      return;
    }
    register();
    setType();
    setName();
  }

  private void setType() {
    String[] names = getClass().toString().split("\\.");
    type = names[names.length - 1];
  }

  public boolean typeEquals(String typeCheck) {
    return type.toLowerCase().contains(typeCheck.toLowerCase());
  }

  private void setName() {
    name = type;
  }

  public String getName() {
    return name;
  }

  public double execute() {
    executed = true;
    return executeCommand();
  }

  /**
   * Runs and executes the commands, returning its double return value
   *
   * @return the double return value
   */
  public abstract double executeCommand();

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

  public boolean isCompleteSub() {
    return args.size() == desiredArgs;
  }

  public Command createCommand(String declaration) {
    try {
      return this.getClass().getConstructor(String.class).newInstance(declaration);
    } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
      //do nothing
      //should never happen
      System.out.println("error in creating command " + getClass());
    }
    return null;
  }

  public boolean isComplete() {
    for (Command c : args) {
      if (!c.isComplete()) {
        return false;
      }
    }
    return isComplete || isCompleteSub();
  }

  public void setIsComplete(boolean comp) {
    isComplete = comp;
  }

  public void addArg(Command arg) {
    args.add(arg);
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
    if (typeEquals("makeuserinstruction")) {
      return true;
    }
    for (Command c : args) {
      if (c.containsDefinition()) {
        return true;
      }
    }
    return false;
  }

  public Command findFirstDef() {
    if (typeEquals("makeuserinstruction") && !executed) { //fixme bad bad bad
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
