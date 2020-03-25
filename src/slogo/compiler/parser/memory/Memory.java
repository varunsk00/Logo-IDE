package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.parser.Command;
import slogo.turtle.Turtle;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Holds all memory for a given compiler - turtles, variables, commands, and display ids such
 * as background color id and palette colors.
 *
 * Assumptions: All inputs (in particular, user commands) are validated in Compiler.
 * This class does almost no sanity checking, and just stores data (as per memory)
 *
 * Dependencies: Command, CommandMemory, Variable Memory, TurtleMemory, DisplayMemory, Turtle
 */
public class Memory {

  public static final int MAX_HISTORY_STORED = 100;

  private ResourceBundle errorMsgs;
  private VariableMemory varMemory;
  private CommandMemory commMemory;
  private TurtleMemory turtleMemory;
  private DisplayMemory displayMemory;
  private List<String> enteredCommands = new ArrayList<>();
  private ArrayDeque<List<String>> enteredStack = new ArrayDeque<>();

  /**
   * Creates new blank slate Memory object
   */
  public Memory() {
    varMemory = new VariableMemory();
    commMemory = new CommandMemory();
    turtleMemory = new TurtleMemory();
    displayMemory = new DisplayMemory();
  }

  /**
   * Copy constructor - creates new Memory object with old command and display memories
   * @param other
   */
  public Memory(Memory other) {
    this();
    commMemory = other.commMemory;
    displayMemory = other.displayMemory;
  }

  /**
   * Sets the error message bundle for this memory
   * @param msgs the resource bundle to be used
   */
  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
    varMemory.setErrorMsgs(errorMsgs);
    commMemory.setErrorMsgs(errorMsgs);
    turtleMemory.setErrorMsgs(errorMsgs);
  }

  /**
   * Saves the state of memory such that it can be reverted to
   * @param input the text code that was entered
   */
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

  /**
   * Reverts to most recent saved state, throwing away current state
   */
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

  /**
   * Returns all entered commands
   * @return all entered commands
   */
  public String getEnteredText() {
    return String.join("\n", enteredCommands);
  }

  /**
   * Gets the double value of a variable
   * @param name name of the variable
   * @return the double value of the variable
   */
  public double getVariable(String name) {
    return varMemory.getVariable(name);
  }

  /**
   * Sets the value of a variable
   * @param name the name of the variable to be set
   * @param value the value of the variable
   */
  public void setVariable(String name, double value) {
    varMemory.setVariable(name, value);
  }

  /**
   * Pushes the memory stack, creating a new scope
   */
  public void pushMemoryStack() {
    varMemory.pushMemoryStack();
  }

  /**
   * Pops the memory stack, deleting the old scope
   */
  public void popMemoryStack() {
    varMemory.popMemoryStack();
  }

  /**
   * Returns all extant variable names
   * @return all extant variable names
   */
  public Collection<String> getAllVariableNames() {
    return varMemory.getAllVariableNames();
  }

  /**
   * Saves a name to a user defined command
   * @param name the name of the command
   * @param c the user defined command to be stored
   */
  public void setUserDefinedCommand(String name, Command c) {
    commMemory.setUserDefinedCommand(name, c);
  }

  /**
   * Gets a user defined command based on its name
   * @param name the name of the command
   * @return the command associated with the name
   */
  public Command getUserDefinedCommand(String name) {
    return commMemory.getUserDefinedCommand(name);
  }

  /**
   * Returns the variable arguments associated with a user defined command
   * @param name the name of the command
   * @return the list of the variable arguments
   */
  public List<String> getCommandVariables(String name) {
    return commMemory.getCommandVariables(name);
  }

  /**
   * Sets the variables to be associated with a user defined command
   * @param name the name of the command
   * @param list the list of variables to be used as arguments
   */
  public void setUserDefinedCommandVariables(String name, List<String> list) {
    commMemory.setUserDefinedCommandVariables(name, list);
  }

  /**
   * Returns all user defined commands
   * @return all user defined commands
   */
  public Collection<String> getAllUserDefinedCommands() {
    return commMemory.getAllUserDefinedCommands();
  }

  /**
   * Given a turtle id, return the corresponding turtle object
   * @param id the id of the turtle
   * @return the Turtle object corresponding to that ID
   */
  public Turtle getTurtleByID(int id) {
    return turtleMemory.getTurtleByID(id);
  }

  /**
   * Returns the current active turtle
   * @return the current active turtle
   */
  public Turtle getCurrentTurtle() {
    return turtleMemory.getCurrentTurtle();
  }

  /**
   * Sets the current active turtle to the given id
   * @param id the turtle to be set to active
   */
  public void setCurrentTurtle(int id) {
    turtleMemory.setCurrentTurtle(id);
  }

  /**
   * Returns all extant turtle IDs
   * @return all extant turtle IDs
   */
  public Collection<Integer> getAllTurtleIDs() {
    return turtleMemory.getAllTurtleIDs();
  }

  /**
   * Toggles the active state of the turtle associated with the given ID
   * @param id the id of the turtle to toggle
   */
  public void toggleActiveTurtle(int id) {
    turtleMemory.toggleActiveTurtle(id);
  }

  /**
   * Pushes the turtle stack during an ask
   * @param newActives the new turtle to be set to active
   */
  public void pushTurtleStack(List<Integer> newActives) {
    turtleMemory.pushTurtleStack(newActives);
  }

  /**
   * Pops the turtle stack, returning to the previous active turtles
   */
  public void popTurtleStack() {
    turtleMemory.popTurtleStack();
  }

  /**
   * Sets a new set of global active turtles
   * @param newActives
   */
  public void tellTurtleStack(List<Integer> newActives) {
    turtleMemory.tellTurtleStack(newActives);
  }

  /**
   * Returns all currently active turtle ids
   * @return all currently active turtle ids
   */
  public List<Integer> getActiveTurtleIDs() {
    return turtleMemory.getActiveTurtleIDs();
  }

  /**
   * Gets the current active turtle ID
   * @return the current active turtle ID
   */
  public int getCurrentTurtleID() {
    return turtleMemory.getCurrentTurtleID();
  }

  /**
   * Returns the id of the current background color
   * @return the id of the current background color
   */
  public int getBackgroundColor() {
    return displayMemory.getBackgroundColor();
  }

  /**
   * Sets the id of the current background color
   * @param backgroundColor the id to be set
   */
  public void setBackgroundColor(int backgroundColor) {
    displayMemory.setBackgroundColor(backgroundColor);
  }

  /**
   * Sets the future pen color for all new turtles
   * @param penColorIndex the index of the new pen color
   */
  public void setPenColorIndex(int penColorIndex) {
    turtleMemory.setPenColorIndex(penColorIndex);
  }

  /**
   * Sets the future shape for all new turtles
   * @param shapeIndex the index of the future shape
   */
  public void setShapeIndex(int shapeIndex) {
    turtleMemory.setShapeIndex(shapeIndex);
  }

  /**
   * Adds a new RGB color to the palette
   * @param idx the index of the color
   * @param r the red value
   * @param g the green value
   * @param b the blue value
   */
  public void addColor(int idx, int r, int g, int b) {
    displayMemory.addColor(idx, r, g, b);
  }

  /**
   * Gets a map mapping the IDs to the [r,g,b] colors in the current palette
   * @return
   */
  public Map<Integer, int[]> getPaletteColors() {
    return displayMemory.getPaletteColors();
  }
}
