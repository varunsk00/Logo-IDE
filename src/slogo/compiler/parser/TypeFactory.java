package slogo.compiler.parser;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidSyntaxException;

public class TypeFactory {

  private static Map<String, Command> registeredCommands = new HashMap<>();
  private static ResourceBundle errorMsgs;

  private TypeFactory() {
    //do nothing
  }

  public static void registerCommand(String id, Command comm) {
    registeredCommands.put(id, comm);
  }

  public static Command createCommand(String name, String declaration) {
    try {
      return registeredCommands.get(name).createCommand(declaration);
    } catch (NullPointerException e) {
      throw new InvalidSyntaxException(String.format(errorMsgs.getString("SeenNotParsed"), name));
    }
  }

  public static void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }
}
