package compiler;

import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

  private static Map<String, Command> registeredCommands = new HashMap<>();

  public static void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public static Command createCommand(String name, String declaration) {
    return registeredCommands.get(name).createCommand(declaration);
  }
}
