package slogo.compiler;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.List;
import slogo.compiler.exceptions.InvalidSyntaxException;
import slogo.compiler.exceptions.InvalidTurtleException;
import slogo.compiler.exceptions.StackOverflowException;
import slogo.compiler.exceptions.StackUnderflowException;
import slogo.compiler.exceptions.UnknownVariableException;
import java.util.HashMap;
import java.util.Map;
import slogo.turtle.Turtle;

public class Memory {

  private static ArrayDeque<Map<String, Double>> variableStack = new ArrayDeque<>();
  private static Map<String, Command> userDefinedCommandMap = new HashMap<>();
  private static Map<String, List<String>> userDefinedCommandVariablesMap = new HashMap<>();
  private static Map<String, Turtle> turtleMap = new HashMap<>();
  private static String currentTurtleID;

  static {
    variableStack.push(new HashMap<>());
  }

  public static double getVariable(String name) {
    Double ret = variableStack.peek().getOrDefault(name, null);
    if (ret == null) {
      throw new UnknownVariableException("The variable " + name + " has not yet been defined.");
    }
    return ret;
  }

  public static void setVariable(String name, double value) {
    variableStack.peek().put(name, value);
  }

  public static void pushMemoryStack() {
    HashMap<String, Double> newLayer = new HashMap<>(variableStack.peek());
    variableStack.push(newLayer);
    if (variableStack.size() > Compiler.MAX_RECURSION_DEPTH) {
      while (variableStack.size() > 1) {
        variableStack.pop();
      }
      throw new StackOverflowException("Max recursion depth: (" + Compiler.MAX_RECURSION_DEPTH + ") exceeded.");
    }
  }

  public static void popMemoryStack() {
    variableStack.pop();
    if (variableStack.size() < 1) {
      throw new StackUnderflowException("Attempted to pop global memory on stack");
    }
  }

  public static void putIfAbsent(String name) {
    variableStack.peek().putIfAbsent(name, 0.0);
  }

  public static void setUserDefinedCommand(String name, Command c) {
    userDefinedCommandMap.put(name, c);
  }

  public static Command getUserDefinedCommand(String name) {
    Command ret = userDefinedCommandMap.getOrDefault(name, null);
    if (ret == null) {
      throw new InvalidSyntaxException("Identifier (" + name + ") not recognized.");
    }
    return ret;
  }

  public static List<String> getCommandVariables(String name) {
    List<String> ret = userDefinedCommandVariablesMap.getOrDefault(name, new ArrayList<>());
    /*if (ret == null) {
      throw new InvalidSyntaxException("Identifier (" + name + ") not recognized.");
    }*/
    return ret;
  }

  public static void setUserDefinedCommandVariables(String name, List<String> list) {
    userDefinedCommandVariablesMap.put(name, list);
  }

  public static void addTurtle(String id, Turtle t) {
    turtleMap.put(id, t);
    currentTurtleID = id;
  }

  public static Turtle getTurtleByID(String id) {
    Turtle ret = turtleMap.getOrDefault(id, null);
    if (ret == null) {
      throw new InvalidTurtleException("Turtle (" + id + ") does not exist.");
    }
    return ret;
  }

  public static Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }

}
