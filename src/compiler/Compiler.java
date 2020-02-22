package compiler;

import compiler.exceptions.CompilerException;
import compiler.exceptions.InvalidSyntaxException;
import compiler.exceptions.StackOverflowException;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import java.util.zip.Deflater;

public class Compiler {

  private static final String LANGUAGES_PACKAGE_EXTENSION = "resources.languages.";
  private static final String RESOURCES_PACKAGE = LANGUAGES_PACKAGE_EXTENSION;
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String SYNTAX_FILE = "Syntax";

  private List<Entry<String, Pattern>> myTypes;
  private List<Entry<String, Pattern>> myCommands;

  public Compiler() {
    myTypes = new ArrayList<>();
    myCommands = new ArrayList<>();
    addPatterns(DEFAULT_LANGUAGE, myCommands); //FIXME add support for multiple languages
    addPatterns(SYNTAX_FILE, myTypes);
    //FIXME add resource file validator
    //FIXME add error msg strings

    initAllCommands();
  }

  private void initAllCommands() { //FIXME delete this trash
    new ConstantCommand("");
    new VariableCommand("");
    new MakeVariableCommand("");
  }

  public String execute(String input) {
    try {
      return "" + parse(input).execute();
    } catch (CompilerException e) {
      return e.toString();
    }
  }


  public Command parse(String input) {
    ArrayDeque<Command> stack = new ArrayDeque<>();
    for (String word : input.split(" ")) {
      Command comm = getCommandFromString(word);
      stack.push(comm);
      while (stack.peek().isComplete()) {
        Command arg = stack.pop();
        if (stack.peek()==null) {
          if (arg.isComplete()) {
            return arg;
          }
          throw new InvalidSyntaxException("Ran out of commands to parse before finishing given commands.");
        }
        stack.peek().addArg(arg);
        if (stack.size() >= StackOverflowException.MAX_RECURSION_DEPTH) {
          throw new StackOverflowException(
              "Max recursion depth: (" + StackOverflowException.MAX_RECURSION_DEPTH
                  + ") exceeded.");
        }
      }
    }
    return stack.getLast();
  }

  private Command getCommandFromString(String str) {
    str = str.toLowerCase();
    String type = getSymbol(str, myTypes);
    if (!type.equals("Command")) {
      return TypeFactory.createCommand(type, str);
    }
    String commType = getSymbol(str, myCommands);
    return CommandFactory.createCommand(commType, str);
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns(String filename, List<Entry<String, Pattern>> list) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + filename);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      list.add(new SimpleEntry<>(key,
          // THIS IS THE IMPORTANT LINE
          Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  public String getSymbol(String text, List<Entry<String, Pattern>> list) {
    for (Entry<String, Pattern> e : list) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    throw new InvalidSyntaxException("Identifier (" + text + ") not recognized.");
  }


  // Returns true if the given text matches the given regular expression pattern
  private boolean match(String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }


}
