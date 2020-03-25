package slogo.compiler.parser.memory;

import java.util.List;
import java.util.Map;
import slogo.compiler.parser.Command;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Store a state of the CommandMemory such that it can later be reverted to
 */
public class CommandMemoryState {

  private Map<String, Command> commands;
  private Map<String, List<String>> vars;

  public CommandMemoryState(Map<String, Command> c, Map<String, List<String>> v) {
    commands = c;
    vars = v;
  }

  /**
   * Returns stored commands map
   * @return stored commands map
   */
  public Map<String, Command> getCommands() {
    return commands;
  }

  /**
   * Returns stored variables map
   * @return stored variables map
   */
  public Map<String, List<String>> getVars() {
    return vars;
  }
}
