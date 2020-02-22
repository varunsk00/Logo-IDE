package slogo.compiler;

import slogo.compiler.exceptions.InvalidSyntaxException;
import java.util.HashMap;
import java.util.Map;

public class CommandFactory {

  private static Map<String, Command> registeredCommands = new HashMap<>();

  public static void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public static Command createCommand(String name, String declaration) {
    try {
      return registeredCommands.get(name).createCommand(declaration);
    } catch (NullPointerException e) {
      throw new InvalidSyntaxException("Command (" + name + ") recognized but not parsed.");
    }
  }
}
