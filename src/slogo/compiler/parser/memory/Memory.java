package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;

public class Memory {

  public static final int MAX_HISTORY_STORED = 100;

  private ResourceBundle errorMsgs;
  private VariableMemory varMemory;
  private CommandMemory commMemory;
  private TurtleMemory turtleMemory;
  private DisplayMemory displayMemory;
  private List<String> enteredCommands = new ArrayList<>();
  private ArrayDeque<List<String>> enteredStack = new ArrayDeque<>();

  public Memory() {
    varMemory = new VariableMemory();
    commMemory = new CommandMemory();
    turtleMemory = new TurtleMemory();
    displayMemory = new DisplayMemory();
  }

  public Memory(Memory other) {
    this();
    commMemory = other.commMemory;
    displayMemory = other.displayMemory;
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
    varMemory.setErrorMsgs(errorMsgs);
    commMemory.setErrorMsgs(errorMsgs);
    turtleMemory.setErrorMsgs(errorMsgs);
  }

  public void save(String input) {
    saveSelf();
    varMemory.save();
    commMemory.save();
    turtleMemory.save();
    displayMemory.save();
    enteredCommands.add(input);
  }

  private void saveSelf() {
    List<String> list = new ArrayList<>(enteredCommands);
    enteredStack.push(list);
    while (enteredStack.size() > MAX_HISTORY_STORED) {
      enteredStack.removeLast();
    }
  }

  public void undo() {
    undoSelf();
    varMemory.undo();
    commMemory.undo();
    turtleMemory.undo();
    displayMemory.undo();
  }

  private void undoSelf() {
    if (!enteredCommands.isEmpty()) {
      enteredCommands = enteredStack.pop();
    }
  }

  public String getEnteredText() {
    return String.join("\n", enteredCommands);
  }

  public double getVariable(String name) {
    return varMemory.getVariable(name);
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

  public Turtle getTurtleByID(int id) {
    return turtleMemory.getTurtleByID(id);
  }

  public Turtle getCurrentTurtle() {
    return turtleMemory.getCurrentTurtle();
  }

  public void setCurrentTurtle(int id) {
    turtleMemory.setCurrentTurtle(id);
  }

  public Collection<Integer> getAllTurtleIDs() {
    return turtleMemory.getAllTurtleIDs();
  }

  public void toggleActiveTurtle(int id) {
    turtleMemory.toggleActiveTurtle(id);
  }

  public void pushTurtleStack(List<Integer> newActives) {
    turtleMemory.pushTurtleStack(newActives);
  }

  public void popTurtleStack() {
    turtleMemory.popTurtleStack();
  }

  public void tellTurtleStack(List<Integer> newActives) {
    turtleMemory.tellTurtleStack(newActives);
  }

  public List<Integer> getActiveTurtleIDs() {
    return turtleMemory.getActiveTurtleIDs();
  }

  public int getCurrentTurtleID() {
    return turtleMemory.getCurrentTurtleID();
  }

  public int getBackgroundColor() {
    return displayMemory.getBackgroundColor();
  }

  public void setBackgroundColor(int backgroundColor) {
    displayMemory.setBackgroundColor(backgroundColor);
  }

  public void setPenColorIndex(int penColorIndex) {
    turtleMemory.setPenColorIndex(penColorIndex);
  }

  public void setShapeIndex(int shapeIndex) {
    turtleMemory.setShapeIndex(shapeIndex);
  }

  public void addColor(int idx, int r, int g, int b) {
    displayMemory.addColor(idx, r, g, b);
  }

  public Map<Integer, int[]> getPaletteColors() {
    return displayMemory.getPaletteColors();
  }
}
