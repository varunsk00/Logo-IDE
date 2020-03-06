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

public class CommandMemory {

  private Map<String, Command> userDefinedCommandMap;
  private Map<String, List<String>> userDefinedCommandVariablesMap;
  private ArrayDeque<CommandMemoryState> historyStack = new ArrayDeque<>();
  private ResourceBundle errorMsgs;

  public CommandMemory() {
    userDefinedCommandMap = new HashMap<>();
    userDefinedCommandVariablesMap = new HashMap<>();
  }

  public void save() {
    Map<String, Command> c = new HashMap<>();
    Map<String, List<String>> v = new HashMap<>();
    for (Entry<String, List<String>> e: userDefinedCommandVariablesMap.entrySet()) {
      v.put(e.getKey(), new ArrayList<>(e.getValue()));
    }
    historyStack.push(new CommandMemoryState(c,v));
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  public void undo() {
    CommandMemoryState state = historyStack.pop();
    userDefinedCommandMap = state.getCommands();
    userDefinedCommandVariablesMap = state.getVars();
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  public void setUserDefinedCommand(String name, Command c) {
    userDefinedCommandMap.put(name, c);
  }

  public Command getUserDefinedCommand(String name) {
    Command ret = userDefinedCommandMap.getOrDefault(name, null);
    /*if (ret == null) {
      throw new InvalidSyntaxException("Identifier (" + name + ") not recognized.");
    }*/
    return ret;
  }

  public List<String> getCommandVariables(String name) {
    List<String> oldList = userDefinedCommandVariablesMap.getOrDefault(name, new ArrayList<>());
    if (oldList == null) {
      return null;
    }
    return new ArrayList<>(oldList);
  }

  public void setUserDefinedCommandVariables(String name, List<String> list) {
    userDefinedCommandVariablesMap.put(name, list);
  }

  public Collection<String> getAllUserDefinedCommands() {
    return userDefinedCommandMap.keySet();
  }

  public Map<String, List<String>> getUserCommandMapCopy() {
    return new HashMap<>(userDefinedCommandVariablesMap);
  }

}
