package compiler;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.concurrent.SynchronousQueue;
import java.util.regex.Pattern;

public class Compiler {
  private static final String LANGUAGES_PACKAGE_EXTENSION = ".resources.languages.";
  private static final String RESOURCES_PACKAGE = Compiler.class.getPackageName() + LANGUAGES_PACKAGE_EXTENSION;
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String SYNTAX_FILE = "Syntax";

  private List<Entry<String, Pattern>> myTypes;
  private List<Entry<String, Pattern>> myCommands;

  public Compiler() {
    myTypes = new ArrayList<>();
    myCommands = new ArrayList<>();
    addPatterns(DEFAULT_LANGUAGE, myTypes);
    addPatterns(SYNTAX_FILE, myCommands); //FIXME add support for multiple languages
    //FIXME add resource file validator
    //FIXME add error msg strings
  }


  public Command parse(String input) {
    ArrayDeque<Command> stack = new ArrayDeque<>();
    for (String word: input.split(" ")) {
      Command comm = getCommandFromString(word);
      stack.push(comm);
      while(stack.peek().isComplete()) {
        Command arg = stack.pop();
        stack.peek().addArg(arg);
      }
    }
    return stack.getLast();
  }

  private Command getCommandFromString(String str) {
    /*
    check type (variable, constant, command, list open/close)
    if not command, create appropriate class and return
    else, lookup command and create corresponding empty class

    setup type map
    setup command map

    iterate over type map
    if matches, create class

    else, iterate over command map
    if matches, create command class

    else, throw invalidsyntaxex
     */
    String type = getSymbol(str, myTypes);
    if (!type.equals("Command")) {
      TypeBuilder typebuilder = new TypeBuilder();
      return typebuilder.createCommand(type, str);
    }
    CommandBuilder comm = new CommandBuilder();
    String commType = getSymbol(str, myCommands);
    return comm.createCommand(commType, str);
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  public void addPatterns (String filename, List<Entry<String, Pattern>> list) {
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
  public String getSymbol (String text, List<Entry<String, Pattern>> list) {
    final String ERROR = "NO MATCH";
    for (Entry<String, Pattern> e : list) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    // FIXME: perhaps throw an exception instead
    return ERROR;
  }


  // Returns true if the given text matches the given regular expression pattern
  private boolean match (String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }


}
