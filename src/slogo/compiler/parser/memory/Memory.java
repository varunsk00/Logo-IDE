package slogo.compiler.parser.memory;

import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;

public class Memory {

  private ResourceBundle errorMsgs;
  private VariableMemory varMemory;
  private CommandMemory commMemory;
  private TurtleMemory turtleMemory;

  public Memory() {
    varMemory = new VariableMemory();
    commMemory = new CommandMemory();
    turtleMemory = new TurtleMemory();
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
    varMemory.setErrorMsgs(errorMsgs);
    commMemory.setErrorMsgs(errorMsgs);
  }

  public double getVariable(String name) {
    return varMemory.getVariable(name);
  }

  public Map<String, Double> getVariableMapCopy() {
    return varMemory.getVariableMapCopy();
  }

  public void setVariable(String name, double value) {
    varMemory.setVariable(name, value);
  }

  public void pushMemoryStack() {
    varMemory.pushMemoryStack();
  }

  public void popMemoryStack() {
    varMemory.popMemoryStack();
  }

  public Collection<String> getAllVariableNames() {
    return varMemory.getAllVariableNames();
  }

  public void setUserDefinedCommand(String name, Command c) {
    commMemory.setUserDefinedCommand(name, c);
  }

  public Command getUserDefinedCommand(String name) {
    return commMemory.getUserDefinedCommand(name);
  }

  public List<String> getCommandVariables(String name) {
    return commMemory.getCommandVariables(name);
  }

  public void setUserDefinedCommandVariables(String name, List<String> list) {
    commMemory.setUserDefinedCommandVariables(name, list);
  }

  public Collection<String> getAllUserDefinedCommands() {
    return commMemory.getAllUserDefinedCommands();
  }

  public Map<String, List<String>> getUserCommandMapCopy() {
    return commMemory.getUserCommandMapCopy();
  }

  public void addTurtle(String id, Turtle t) {
    turtleMemory.addTurtle(id, t);
  }

  public Turtle getTurtleByID(String id) {
    return turtleMemory.getTurtleByID(id);
  }

  public Turtle getCurrentTurtle() {
    return turtleMemory.getCurrentTurtle();
  }


  public Collection<String> getAllTurtleIDs() {
    return turtleMemory.getAllTurtleIDs();
  }

  public Map<String, Turtle> getTurtleMapCopy() {
    return turtleMemory.getTurtleMapCopy();
  }
}
