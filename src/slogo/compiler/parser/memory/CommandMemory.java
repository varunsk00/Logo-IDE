package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Stores all user defined commands and their arguments
 * <p>
 * Assumptions: All inputs are valid and verified elsewhere, as with Memory
 * <p>
 * Dependencies: Command, Memory, CommandMemoryState
 */
public class CommandMemory {

  private Map<String, Command> userDefinedCommandMap;
  private Map<String, List<String>> userDefinedCommandVariablesMap;
  private ArrayDeque<CommandMemoryState> historyStack = new ArrayDeque<>();
  private ResourceBundle errorMsgs;

  /**
   * Creates new CommandMemory
   */
  public CommandMemory() {
    userDefinedCommandMap = new HashMap<>();
    userDefinedCommandVariablesMap = new HashMap<>();
  }

  /**
   * Saves the state of memory such that it can be reverted to
   */
  public void save() {
    Map<String, Command> c = new HashMap<>();
    Map<String, List<String>> v = new HashMap<>();
    for (Entry<String, List<String>> e : userDefinedCommandVariablesMap.entrySet()) {
      v.put(e.getKey(), new ArrayList<>(e.getValue()));
    }
    historyStack.push(new CommandMemoryState(c, v));
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  /**
   * Reverts to most recent saved state, throwing away current state
   */
  public void undo() {
    if (!historyStack.isEmpty()) {
      CommandMemoryState state = historyStack.pop();
      userDefinedCommandMap = state.getCommands();
      userDefinedCommandVariablesMap = state.getVars();
    }
  }

  /**
   * Sets the error message bundle for this memory
   *
   * @param msgs the resource bundle to be used
   */
  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  /**
   * Saves a name to a user defined command
   *
   * @param name the name of the command
   * @param c    the user defined command to be stored
   */
  public void setUserDefinedCommand(String name, Command c) {
    userDefinedCommandMap.put(name, c);
  }

  /**
   * Gets a user defined command based on its name
   *
   * @param name the name of the command
   * @return the command associated with the name
   */
  public Command getUserDefinedCommand(String name) {
    Command ret = userDefinedCommandMap.getOrDefault(name, null);
    /*if (ret == null) {
      throw new InvalidSyntaxException("Identifier (" + name + ") not recognized.");
    }*/
    return ret;
  }

  /**
   * Returns the variable arguments associated with a user defined command
   *
   * @param name the name of the command
   * @return the list of the variable arguments
   */
  public List<String> getCommandVariables(String name) {
    List<String> oldList = userDefinedCommandVariablesMap.getOrDefault(name, new ArrayList<>());
    if (oldList == null) {
      return null;
    }
    return new ArrayList<>(oldList);
  }

  /**
   * Sets the variables to be associated with a user defined command
   *
   * @param name the name of the command
   * @param list the list of variables to be used as arguments
   */
  public void setUserDefinedCommandVariables(String name, List<String> list) {
    userDefinedCommandVariablesMap.put(name, list);
  }

  /**
   * Returns all user defined commands
   *
   * @return all user defined commands
   */
  public Collection<String> getAllUserDefinedCommands() {
    return userDefinedCommandMap.keySet();
  }

}
