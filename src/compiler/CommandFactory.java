package compiler;

import java.util.Map;

public class CommandFactory {
  private static Map<String, Command> registeredCommands;

  public void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public Command createCommand(String name, String declaration) {
    return registeredCommands.get(name).createCommand(declaration);
  }
}
