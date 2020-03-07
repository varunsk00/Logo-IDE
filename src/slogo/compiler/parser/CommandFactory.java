package slogo.compiler.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidSyntaxException;

public class CommandFactory {

  private static Map<String, Command> registeredCommands = new HashMap<>();

  private CommandFactory() {
    //do nothing
  }

  public static void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public static Command createCommand(String name, String declaration) {
      return registeredCommands.get(name).createCommand(declaration);
  }
}
