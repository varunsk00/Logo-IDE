package slogo.compiler.parser;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidSyntaxException;
import slogo.compiler.parser.memory.Memory;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Superclass for all SLogo commands - all text commands are parsed and compiled into
 * Command objects.
 * <p>
 * Assumptions: Declaration is either contains no spaces or is the initialization string.
 * <p>
 * Dependencies: InvalidSyntaxException, CommandFactory, Memory
 */
public abstract class Command {

  protected static final String INITIALIZATION = "this is an initialization string that should never happen";
  protected static final int GROUPING_INVALID = -1;
  protected static final int GROUPING_ITERATIVE = 0;
  protected static final int GROUPING_COMPARISON = 1;
  protected static final int GROUPING_RECURSIVE = 2;

  protected Memory memory;
  protected ArrayList<Command> args;
  protected ResourceBundle errorMsgs;
  protected boolean executed = false;
  protected int desiredArgs;
  protected String name;
  protected String type;
  protected int groupingType;
  private boolean isComplete;

  /**
   * Creates a Command object with the given string declaration
   *
   * @param declaration the text used to declare the command
   */
  public Command(String declaration) {
    args = new ArrayList<>();
    groupingType = GROUPING_ITERATIVE;
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

  /**
   * Returns true if this class is equal to the given type
   *
   * @param typeCheck the type to be compared to
   * @return whether or not this command is the given type
   */
  public boolean typeEquals(String typeCheck) {
    return type.toLowerCase().contains(typeCheck.toLowerCase());
  }

  private void setName() {
    name = type;
  }

  /**
   * Returns the name of the command
   *
   * @return the name of the command
   */
  public String getName() {
    return name;
  }

  /**
   * Executes the command and returns the double return value
   *
   * @return the double return value of the executed command
   */
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

  /**
   * Registers this command in the CommandFactory
   */
  public void register() {
    String className = findClass();
    factoryRegister(className);
  }

  /**
   * Returns the number of desired arguments
   *
   * @return the number of desired arguments
   */
  public int getDesiredArgs() {
    return desiredArgs;
  }

  /**
   * Returns the type of this command
   *
   * @return the type of this command
   */
  public String getType() {
    return type;
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

  /**
   * Sets the memory for this command to the given object
   *
   * @param mem the memory for this command to use
   */
  public void setMemory(Memory mem) {
    memory = mem;
    for (Command c : args) {
      c.setMemory(mem);
    }
  }

  /**
   * Set the error message resource bundle for this command to use
   *
   * @param msgs the error message resource bundle for this command to use
   */
  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  /**
   * Returns true if the command is complete non recursively
   *
   * @return whether or not the command is complete non recursively
   */
  public boolean isCompleteSub() {
    return args.size() == desiredArgs;
  }

  /**
   * Creates a new command given the string declaration
   *
   * @param declaration the text that declares the command
   * @return the created command object
   */
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

  /**
   * Returns true if the command is recursively complete
   *
   * @return whether or not the command is recursively complete
   */
  public boolean isComplete() {
    for (Command c : args) {
      if (!c.isComplete()) {
        return false;
      }
    }
    return isComplete || isCompleteSub();
  }

  /**
   * Overrides logic and declares this command as the input boolean
   *
   * @param comp the truth value of the completeness of the command
   */
  public void setIsComplete(boolean comp) {
    isComplete = comp;
  }

  /**
   * Appends a command argument to this command
   *
   * @param arg the argument to be added
   */
  public void addArg(Command arg) {
    args.add(arg);
  }

  /**
   * Sets the nth argument to the given command
   *
   * @param c the argument to be added
   * @param n the index of the argument to be set, 0 indexed
   */
  public void setArg(Command c, int n) {
    try {
      if (args.size() == n) {
        args.add(c);
      } else {
        args.set(n, c);
      }
    } catch (IndexOutOfBoundsException e) {
      throw new InvalidSyntaxException("Attempted to set argument out of bounds"); //fixme error msg
    }
  }

  /**
   * Returns the value of the grouping type of the command
   *
   * @return the value of the grouping type of the command
   */
  public int getGroupingType() {
    return groupingType;
  }


  /**
   * Returns the list of command arguments
   *
   * @return the list of command arguments
   */
  public List<Command> getArgs() {
    return args;
  }

  /**
   * Returns the string interpretation of the command
   *
   * @return the string interpretation of the command
   */
  @Override
  public String toString() {
    StringBuilder ret = new StringBuilder(this.getClass().getName() + " ");
    /*for (Command i : args) {
      ret.append(i.toString()).append(" ");
    }*/
    return ret.toString();
  }

  /**
   * Returns true if the command contains a user command definition
   *
   * @return true if the command contains a user command definition
   */
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

  /**
   * Returns the first definition in the command, or null if none exist
   *
   * @return the first definition in the command, or null if none exist
   */
  public Command findFirstDef() {
    if (typeEquals("makeuserinstruction") && !executed) {
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
