package slogo.compiler.parser;

import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import slogo.compiler.exceptions.CompilerException;
import slogo.compiler.exceptions.InvalidSyntaxException;
import slogo.compiler.exceptions.StackOverflowException;
import slogo.compiler.parser.memory.Memory;
import slogo.turtle.Turtle;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Takes in SLogo code and compiles it into a tree of Command objects, then executes the
 * tree and returns the string version of the double result, or the string version of the error
 * message thrown.
 * <p>
 * Assumptions: All text entered is line separated and space separated, all text encountered is
 * contained within the syntax regex, all resource files are valid and complete.
 * <p>
 * Dependencies: CompilerException, Command, CommandFactory, TypeFactory, Memory
 */
public class Compiler {

  public static final int MAX_RECURSION_DEPTH = 1000;
  private static final String LANGUAGES_PACKAGE_EXTENSION = "slogo.resources.languages.";
  private static final String RESOURCES_PACKAGE = LANGUAGES_PACKAGE_EXTENSION + "syntax.";
  private static final String ERROR_MESSAGES_PACKAGE = LANGUAGES_PACKAGE_EXTENSION + "exceptions.";
  private static final String DEFAULT_LANGUAGE = "English";
  private static final String SYNTAX_FILE = "Syntax";
  private List<Entry<String, Pattern>> myTypes;
  private List<Entry<String, Pattern>> myCommands;
  private Memory memory;
  private ResourceBundle errorMsgs;

  /**
   * Creates a new blank slate compiler - there is 1 turtle, no variables, and no user defined
   * commands
   */
  public Compiler() {
    myTypes = new ArrayList<>();
    myCommands = new ArrayList<>();
    memory = new Memory();
    setLanguage(DEFAULT_LANGUAGE);
    addPatterns(SYNTAX_FILE, myTypes);
    //FIXME add resource file validator
    initAllCommands();
  }

  /**
   * Copy constructor - makes a new compiler with the same language and memory as another. Copies
   * user defined commands, but not turtles or variables
   *
   * @param other
   */
  public Compiler(Compiler other) {
    myTypes = new ArrayList<>(other.myTypes);
    myCommands = new ArrayList<>(other.myCommands);
    memory = new Memory(other.memory);
  }

  private void initAllCommands() { //uses the reflections library
    Reflections reflections = new Reflections("");//Compiler.class.getPackageName());
    Set<Class<? extends Command>> allClasses = reflections.getSubTypesOf(Command.class);

    for (Class c : allClasses) {
      try {
        //System.out.println(c);
        if (!Modifier.isAbstract(c.getModifiers())) {
          Command a = (Command) c.getConstructor(String.class).newInstance(Command.INITIALIZATION);
          a.register();
        }
      } catch (Exception e) {
        //do nothing. This is okay because I'm just trying to initialize every command class
        //exceptions thrown by the constructors et al are okay
        System.out.println("Maybe something bad happened? " + e);
      }
    }
  }

  /**
   * Sets the language of the compiler to a new language
   *
   * @param lang the language to be set
   */
  public void setLanguage(String lang) {
    myCommands.clear();
    addPatterns(lang, myCommands);
    try {
      errorMsgs = ResourceBundle.getBundle(ERROR_MESSAGES_PACKAGE + lang + "_error");
    } catch (MissingResourceException e) {
      errorMsgs = ResourceBundle.getBundle(ERROR_MESSAGES_PACKAGE + DEFAULT_LANGUAGE + "_error");
    }
    memory.setErrorMsgs(errorMsgs);
  }

  /**
   * Parses and executes text SLogo code
   *
   * @param input the code to be executed
   * @return the string version of the double result, or the string error message
   */
  public String execute(String input) {
    save(input);
    input = spliceInput(input);
    try {
      Command comm = parse(input);
      if (!comm.isComplete()) {
        throw new InvalidSyntaxException(
            String.format(errorMsgs.getString("IncompleteCommand"), input));
      }
      if (comm.containsDefinition()) {
        comm = rerunParsing(comm, input);
      }
      return "" + comm.execute();
    } catch (CompilerException e) {
      return e.toString();
    }
  }

  private String spliceInput(String input) {
    String[] lines = input.split(getNewline());
    StringBuilder noComment = new StringBuilder();
    for (String line : lines) {
      try {
        if (!getSymbol(line, myTypes).equals("Comment")) {
          noComment.append(line);
        }
      } catch (InvalidSyntaxException e) {
        noComment.append(line); //If it throws an exception, it's not a comment.
        //Later code will handle the syntax checking
      }
    }
    String ret = noComment.toString();
    ret = ret.replaceAll("\\[", " [ ");
    ret = ret.replaceAll("]", " ] ");
    ret = ret.replaceAll(":", " :");
    return "[ " + ret + " ]";
  }

  private Command rerunParsing(Command comm, String input) {
    Command def = comm.findFirstDef();
    while (def != null) {
      def.execute();
      def = comm.findFirstDef();
    }
    comm = parse(input);
    if (!comm.isComplete()) {
      throw new InvalidSyntaxException(
          String.format(errorMsgs.getString("IncompleteCommand"), input));
    }
    return comm;
  }

  /**
   * Takes text SLogo code and parses it into a Command object
   *
   * @param input the code to be parsed
   * @return the Command object that was created
   */
  public Command parse(String input) {
    String defineFlag = "";
    ArrayDeque<Command> stack = new ArrayDeque<>();
    for (String word : input.split(getWhitespace())) {
      Command comm = getCommandFromString(word);
      defineFlag = checkRecursion(defineFlag, comm, word);
      stack.push(comm);
      Command ret = collapseStack(stack);
      if (ret != null) {
        return ret;
      }
      if (stack.size() >= MAX_RECURSION_DEPTH) {
        throw new StackOverflowException(
            String.format(errorMsgs.getString("StackOverflow"), MAX_RECURSION_DEPTH));
      }
    }
    return stack.getLast();
  }

  private String checkRecursion(String defineFlag, Command comm, String word) {
    if (defineFlag.equals("user")) {
      defineFlag = "";
      markDefinition(comm, word);
    } else if (defineFlag.equals("group")) {
      defineFlag = "";
      comm.setIsComplete(true);
    }
    if (comm.typeEquals("makeuserinstruction")) {
      defineFlag = "user";
    } else if (comm.typeEquals("groupstart")) {
      defineFlag = "group";
    }
    return defineFlag;
  }

  private void markDefinition(Command comm, String word) {
    if (comm.typeEquals("commandtype")) {
      comm.setIsComplete(true);
    } else {
      throw new InvalidSyntaxException(
          String.format(errorMsgs.getString("RedefineBuiltin"), word));
    }
  }

  private Command collapseStack(ArrayDeque<Command> stack) {
    while (stack.peek().isComplete()) {
      Command arg = stack.pop();
      if (stack.peek() == null) {
        if (arg.isComplete()) {
          return arg;
        }
        throw new InvalidSyntaxException(errorMsgs.getString("OutOfCommands"));
      }
      stack.peek().addArg(arg);
    }
    return null;
  }

  private String getWhitespace() {
    for (Entry<String, Pattern> e : myTypes) {
      if (e.getKey().equals("Whitespace")) {
        return e.getValue().toString();
      }
    }
    throw new CompilerException(errorMsgs.getString("WhitespaceNotFound"));
  }

  private String getNewline() {
    for (Entry<String, Pattern> e : myTypes) {
      if (e.getKey().equals("Newline")) {
        return e.getValue().toString();
      }
    }
    throw new CompilerException(errorMsgs.getString("NewlineNotFound"));
  }

  private Command getCommandFromString(String str) {
    Command ret = null;
    str = str.toLowerCase();
    String type = getSymbol(str, myTypes);
    try {
      if (!type.equals("Command")) { //FIXME magic val
        ret = TypeFactory.createCommand(type, str);
      } else {
        try {
          String commType = getSymbol(str, myCommands);
          ret = CommandFactory.createCommand(commType, str);
        } catch (InvalidSyntaxException e) {
          ret = TypeFactory.createCommand("Command", str); //FIXME magic val
        }
      }
    } catch (NullPointerException e) {
      throw new InvalidSyntaxException(String.format(errorMsgs.getString("SeenNotParsed"), type));
    }
    ret.setMemory(memory);
    ret.setErrorMsgs(errorMsgs);
    return ret;
  }

  /**
   * Adds the given resource file to this language's recognized types
   */
  private void addPatterns(String filename, List<Entry<String, Pattern>> list) {
    ResourceBundle resources = ResourceBundle.getBundle(RESOURCES_PACKAGE + filename);
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      list.add(new SimpleEntry<>(key, Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
  }

  /**
   * Returns language's type associated with the given text if one exists
   */
  private String getSymbol(String text, List<Entry<String, Pattern>> list) {
    for (Entry<String, Pattern> e : list) {
      if (match(text, e.getValue())) {
        return e.getKey();
      }
    }
    throw new InvalidSyntaxException(
        String.format(errorMsgs.getString("IdentifierNotRecognized"), text));
  }

  // Returns true if the given text matches the given regular expression pattern
  private boolean match(String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }

  /**
   * Returns all entered variable names
   *
   * @return all entered variable names
   */
  public Collection<String> getAllVariableNames() {
    return memory.getAllVariableNames();
  }

  /**
   * Returns the names of all the user defined commands
   *
   * @return the names of all the user defined commands
   */
  public Collection<String> getAllUserDefinedCommands() {
    return memory.getAllUserDefinedCommands();
  }

  /**
   * Returns all extant turtle IDs
   *
   * @return all extant turtle IDs
   */
  public Collection<Integer> getAllTurtleIDs() {
    return memory.getAllTurtleIDs();
  }

  /**
   * Given a turtle id, return the corresponding turtle object
   *
   * @param id the id of the turtle
   * @return the Turtle object corresponding to that ID
   */
  public Turtle getTurtleByID(int id) {
    return memory.getTurtleByID(id);
  }

  /**
   * Given the name of a variable, returns its value
   *
   * @param name the name of the variable
   * @return the value of the variable
   */
  public double getVariable(String name) {
    return memory.getVariable(name);
  }

  /**
   * Given the name of a user defined command, returns a list of its variable arguments
   *
   * @param name the name of the user defined command
   * @return the arguments of the user defined command
   */
  public List<String> getCommandVariables(String name) {
    return memory.getCommandVariables(name);
  }

  /**
   * Returns the id of the current background color
   *
   * @return the id of the current background color
   */
  public int getBackgroundColor() {
    return memory.getBackgroundColor();
  }

  /**
   * Sets the id of the current background color
   *
   * @param backgroundColor the id to be set
   */
  public void setBackgroundColor(int backgroundColor) {
    memory.setBackgroundColor(backgroundColor);
  }

  /**
   * Gets a map mapping the IDs to the [r,g,b] colors in the current palette
   *
   * @return
   */
  public Map<Integer, int[]> getPaletteColors() {
    return memory.getPaletteColors();
  }

  /**
   * Undoes the most recent command.
   */
  public void undo() {
    memory.undo();
  }

  private void save(String input) {
    memory.save(input);
  }

  /**
   * Returns all commands entered
   *
   * @return all commands entered
   */
  public String getEnteredText() {
    return memory.getEnteredText();
  }

  /**
   * Toggles the active state of the turtle associated with the given ID
   *
   * @param id the id of the turtle to toggle
   */
  public void toggleActiveTurtle(int id) {
    memory.toggleActiveTurtle(id);
  }

}
