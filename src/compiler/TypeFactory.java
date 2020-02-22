package compiler;

import java.util.Map;

public class TypeFactory {
  private static Map<String, Command> registeredCommands;

  public void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public Command createCommand(String name, String declaration) {
    return registeredCommands.get(name).createCommand(declaration);
  }
}
