package slogo.compiler;

import static slogo.compiler.Compiler.MAX_RECURSION_DEPTH;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.compiler.exceptions.InvalidTurtleException;
import slogo.compiler.exceptions.StackOverflowException;
import slogo.compiler.exceptions.StackUnderflowException;
import slogo.compiler.exceptions.UnknownVariableException;
import slogo.compiler.types.ListStartType;
import slogo.turtle.Turtle;

public class Memory {

  private ArrayDeque<Map<String, Double>> variableStack = new ArrayDeque<>();
  private Map<String, Command> userDefinedCommandMap = new HashMap<>();
  private Map<String, List<String>> userDefinedCommandVariablesMap = new HashMap<>();
  private Map<String, Turtle> turtleMap = new HashMap<>();
  private String currentTurtleID;

  public Memory() {
    variableStack.push(new HashMap<>());
  }

  public double getVariable(String name) {
    Double ret = variableStack.peek().getOrDefault(name, null);
    if (ret == null) {
      throw new UnknownVariableException("The variable " + name + " has not yet been defined.");
    }
    return ret;
  }

  public Map<String, Double> getVariableMapCopy() {
    return new HashMap<>(variableStack.peek());
  }

  public void setVariable(String name, double value) {
    variableStack.peek().put(name, value);
  }

  public void pushMemoryStack() {
    HashMap<String, Double> newLayer = new HashMap<>(variableStack.peek());
    variableStack.push(newLayer);
    if (variableStack.size() > MAX_RECURSION_DEPTH) {
      while (variableStack.size() > 1) {
        variableStack.pop();
      }
      throw new StackOverflowException(
          "Max recursion depth: (" + MAX_RECURSION_DEPTH + ") exceeded.");
    }
  }

  public void popMemoryStack() {
    variableStack.pop();
    if (variableStack.isEmpty()) {
      throw new StackUnderflowException("Attempted to pop global memory on stack");
    }
  }

  public void putIfAbsent(String name) {
    variableStack.peek().putIfAbsent(name, 0.0);
  }

  public void setUserDefinedCommand(String name, Command c) {
    userDefinedCommandMap.put(name, c);
  }

  public Command getUserDefinedCommand(String name) {
    Command ret = userDefinedCommandMap.getOrDefault(name, null);
    /*if (ret == null) {
      throw new InvalidSyntaxException("Identifier (" + name + ") not recognized.");
    }*/
    return ret;
  }

  public List<String> getCommandVariables(String name) {
    List<String> oldList = userDefinedCommandVariablesMap.getOrDefault(name, new ArrayList<>());
    if (oldList == null) {
      return null;
    }
    return new ArrayList<>(oldList);
  }

  public void setUserDefinedCommandVariables(String name, List<String> list) {
    userDefinedCommandVariablesMap.put(name, list);
  }

  public void addTurtle(String id, Turtle t) {
    turtleMap.put(id, t);
    currentTurtleID = id;
  }

  public Turtle getTurtleByID(String id) {
    Turtle ret = turtleMap.getOrDefault(id, null);
    if (ret == null) {
      throw new InvalidTurtleException("Turtle (" + id + ") does not exist.");
    }
    return ret;
  }

  public Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }

  public Collection<String> getAllVariableNames() {
    return variableStack.peek().keySet();
  }

  public Collection<String> getAllUserDefinedCommands() {
    return userDefinedCommandMap.keySet();
  }

  public Collection<String> getAllTurtleIDs() {
    return turtleMap.keySet();
  }

  public Map<String, List<String>> getUserCommandMapCopy() {
    return new HashMap<>(userDefinedCommandVariablesMap);
  }

  //FIXME does this do a copy? should it?
  public Map<String, Turtle> getTurtleMapCopy() {
    return new HashMap<>(turtleMap);
  }
}
