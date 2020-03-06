package slogo.compiler.parser.memory;

import java.util.List;
import java.util.Map;
import slogo.compiler.parser.Command;

public class CommandMemoryState {

  private Map<String, Command> commands;
  private Map<String, List<String>> vars;

  public CommandMemoryState(Map<String, Command> c, Map<String, List<String>> v) {
    commands = c;
    vars = v;
  }

  public Map<String, Command> getCommands() {
    return commands;
  }

  public Map<String, List<String>> getVars() {
    return vars;
  }
}
