package slogo.compiler.parser;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Factory to create command objects that correspond to data types (e.g. variable, number
 * list, user command)
 *
 * Commands are registered with the registerCommand() method and created with the createCommand() method.
 *
 * Assumptions: No registered commands are abstract objects.
 *
 * Dependencies: Command
 */
public class TypeFactory {

  private static Map<String, Command> registeredCommands = new HashMap<>();

  private TypeFactory() {
    //do nothing
  }

  /**
   * Registers a command to be created later
   * @param id The string name to be associated with that command type
   * @param comm The command, whose type will be registered
   */
  public static void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  /**
   * Create a command of the type registered to the given name, using the given declaration
   * @param name name of the command to be created
   * @param declaration the text declaration of the command
   * @return the created command
   */
  public static Command createCommand(String name, String declaration) {
    return registeredCommands.get(name).createCommand(declaration);
  }
}
