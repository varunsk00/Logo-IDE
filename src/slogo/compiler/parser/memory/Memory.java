package slogo.compiler.parser.memory;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.StackUnderflowException;
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

  public Memory(Memory other) {
    this();
    commMemory = other.commMemory;
    varMemory = other.varMemory;
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
    varMemory.setErrorMsgs(errorMsgs);
    commMemory.setErrorMsgs(errorMsgs);
    turtleMemory.setErrorMsgs(errorMsgs);
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

  public void addTurtle(int id) {
    turtleMemory.addTurtle(id);
  }

  public Turtle getTurtleByID(int id) {
    return turtleMemory.getTurtleByID(id);
  }

  public void setCurrentTurtle(int id) {
    turtleMemory.setCurrentTurtle(id);
  }

  public Turtle getCurrentTurtle() {
    return turtleMemory.getCurrentTurtle();
  }

  public Collection<Integer> getAllTurtleIDs() {
    return turtleMemory.getAllTurtleIDs();
  }

  public Map<Integer, Turtle> getTurtleMapCopy() {
    return turtleMemory.getTurtleMapCopy();
  }

  public void pushTurtleStack(List<Integer> newActives)  {
    turtleMemory.pushTurtleStack(newActives);
  }

  public void popTurtleStack() {
    turtleMemory.popTurtleStack();
  }

  public void tellTurtleStack(List<Integer> newActives) {
    turtleMemory.tellTurtleStack(newActives);
  }

  public List<Integer> getActiveTurtleIDs(){
    return turtleMemory.getActiveTurtleIDs();
  }

  public int getCurrentTurtleID() {
    return turtleMemory.getCurrentTurtleID();
  }
}
