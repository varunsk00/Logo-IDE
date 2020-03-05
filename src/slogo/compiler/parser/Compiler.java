package slogo.compiler.parser;

import java.io.File;
import java.io.FileNotFoundException;
import java.lang.reflect.Modifier;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map.Entry;
import java.util.MissingResourceException;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Pattern;
import org.reflections.Reflections;
import slogo.compiler.control.MakeUserInstructionCommand;
import slogo.compiler.exceptions.CompilerException;
import slogo.compiler.exceptions.InvalidSyntaxException;
import slogo.compiler.exceptions.StackOverflowException;
import slogo.compiler.parser.memory.Memory;
import slogo.compiler.types.CommandType;
import slogo.turtle.Turtle;

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

  public Compiler() {
    myTypes = new ArrayList<>();
    myCommands = new ArrayList<>();
    memory = new Memory();
    setLanguage(DEFAULT_LANGUAGE);
    addPatterns(SYNTAX_FILE, myTypes);
    //FIXME add resource file validator
    initAllCommands();
  }

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

  public String execute(String input) {
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
      //throw e;
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
    return "[ " + ret + " ]";
  }

  private Command rerunParsing(Command comm, String input) {
    Command def = comm.findFirstDef();
    while (def != null) {
      def.execute(); //FIXME
      def = comm.findFirstDef();
    }
    comm = parse(input);
    if (!comm.isComplete()) {
      //comm.recPrint(); //fixme
      throw new InvalidSyntaxException(
          String.format(errorMsgs.getString("IncompleteCommand"), input));
    }
    return comm;
  }

  public String executeFile(File file) throws FileNotFoundException {
    String text = getTextFromFile(file);
    return execute(text);
  }

  private String getTextFromFile(File file) throws FileNotFoundException {
    StringBuilder ret = new StringBuilder();
    Scanner scan = new Scanner(file);
    while (scan.hasNextLine()) {
      ret.append(scan.nextLine()).append("\n");
    }
    return ret.toString();
  }


  public Command parse(String input) {
    boolean defineFlag = false;
    ArrayDeque<Command> stack = new ArrayDeque<>();
    for (String word : input.split(getWhitespace())) {
      Command comm = getCommandFromString(word);
      if (defineFlag) {
        if (comm instanceof CommandType) {
          ((CommandType) comm).setBeingDefined(true);
          //FIXME you're a bad person and you should feel bad
          defineFlag = false;
        } else {
          throw new InvalidSyntaxException(
              String.format(errorMsgs.getString("RedefineBuiltin"), word));
        }
      }
      if (comm instanceof MakeUserInstructionCommand) {
        defineFlag = true;
      }
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
    ret.setMemory(memory);
    ret.setErrorMsgs(errorMsgs);
    return ret;
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
    throw new InvalidSyntaxException(
        String.format(errorMsgs.getString("IdentifierNotRecognized"), text));
  }


  // Returns true if the given text matches the given regular expression pattern
  private boolean match(String text, Pattern regex) {
    // THIS IS THE IMPORTANT LINE
    return regex.matcher(text).matches();
  }

  public void addTurtle(int id) {
    memory.addTurtle(id);
  }

  public Collection<String> getAllVariableNames() {
    return memory.getAllVariableNames();
  }

  public Collection<String> getAllUserDefinedCommands() {
    return memory.getAllUserDefinedCommands();
  }

  public Collection<Integer> getAllTurtleIDs() {
    return memory.getAllTurtleIDs();
  }

  public Turtle getTurtleByID(int id) {
    return memory.getTurtleByID(id);
  }

  public double getVariable(String name) {
    return memory.getVariable(name);
  }

  public void setVariable(String name, double val) {
    memory.setVariable(name, val);
  }

  public List<String> getCommandVariables(String name) {
    return memory.getCommandVariables(name);
  }

}
