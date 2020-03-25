package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidTurtleException;
import slogo.compiler.exceptions.StackUnderflowException;
import slogo.turtle.Turtle;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Stores all turtles and relevant turtle data, as well as which turtles are currently active.
 *
 * Assumptions: All inputs are valid and verified elsewhere, as with Memory
 *
 * Dependencies: CompilerException, Turtle, Memory, TurtleMemoryState
 */
public class TurtleMemory {

  private ResourceBundle errorMsgs;
  private Map<Integer, Turtle> turtleMap = new HashMap<>();
  private ArrayDeque<List<Integer>> turtleIDStack = new ArrayDeque<>();
  private ArrayDeque<TurtleMemoryState> historyStack = new ArrayDeque<>();
  private int currentTurtleID;
  private int shapeIndex;
  private int penColorIndex;

  /**
   * Creates new TurtleMemory
   */
  public TurtleMemory() {
    List<Integer> startIDs = new ArrayList<>();
    startIDs.add(1);
    turtleIDStack.push(startIDs);
    addTurtle(1);
  }

  /**
   * Saves the state of memory such that it can be reverted to
   */
  public void save() {
    Map<Integer, Turtle> tur = new HashMap<>();
    for (Entry<Integer, Turtle> e : turtleMap.entrySet()) {
      tur.put(e.getKey(), new Turtle(e.getValue()));
    }
    List<Integer> ids = new ArrayList<>(turtleIDStack.getLast());
    historyStack.push(new TurtleMemoryState(tur, ids, shapeIndex, penColorIndex));
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  /**
   * Reverts to most recent saved state, throwing away current state
   */
  public void undo() {
    if (!historyStack.isEmpty()) {
      TurtleMemoryState state = historyStack.pop();
      turtleMap = state.getTurtleMap();
      penColorIndex = state.getPenColor();
      shapeIndex = state.getShape();
      turtleIDStack.removeLast();
      turtleIDStack.addLast(state.getIDs());
      for (int i : getAllTurtleIDs()) {
        getTurtleByID(i).resetLocation();
        setCurrentTurtle(i);
      }
    }
  }

  /**
   * Sets the error message bundle for this memory
   * @param msgs the resource bundle to be used
   */
  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  /**
   * Creates and adds new turtle with given id
   * @param id the id of the new turtle
   */
  public void addTurtle(int id) {
    addTurtle(id, new Turtle(id));
  }

  /**
   * Adds given turtle with given id
   * @param id the id of the new turtle
   * @param t the turtle object to be added
   */
  public void addTurtle(int id, Turtle t) {
    t.setPenColorIndex(penColorIndex);
    t.setShapeIndex(shapeIndex);
    turtleMap.put(id, t);
    currentTurtleID = id;
  }

  /**
   * Given a turtle id, return the corresponding turtle object
   * @param id the id of the turtle
   * @return the Turtle object corresponding to that ID
   */
  public Turtle getTurtleByID(int id) {
    Turtle ret = turtleMap.getOrDefault(id, null);
    if (ret == null) {
      throw new InvalidTurtleException(
          String.format(errorMsgs.getString("UnknownTurtle"), "" + id));
    }
    return ret;
  }

  /**
   * Returns the current active turtle
   * @return the current active turtle
   */
  public Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }

  /**
   * Sets the current active turtle to the given id
   * @param id the turtle to be set to active
   */
  public void setCurrentTurtle(int id) {
    currentTurtleID = id;
  }

  /**
   * Gets the current active turtle ID
   * @return the current active turtle ID
   */
  public int getCurrentTurtleID() {
    return currentTurtleID;
  }

  /**
   * Returns all extant turtle IDs
   * @return all extant turtle IDs
   */
  public Collection<Integer> getAllTurtleIDs() {
    return turtleMap.keySet();
  }

  /**
   * Returns all currently active turtle ids
   * @return all currently active turtle ids
   */
  public List<Integer> getActiveTurtleIDs() {
    return new ArrayList<>(turtleIDStack.peek());
  }

  /**
   * Toggles the active state of the turtle associated with the given ID
   * @param id the id of the turtle to toggle
   */
  public void toggleActiveTurtle(int id) {
    List<Integer> actives = getActiveTurtleIDs();
    if (actives.contains(id)) {
      actives.remove(Integer.valueOf(id));
    } else {
      actives.add(id);
    }
    tellTurtleStack(actives);
  }

  /**
   * Pushes the turtle stack during an ask
   * @param newActives the new turtle to be set to active
   */
  public void pushTurtleStack(List<Integer> newActives) {
    for (int i : newActives) {
      if (!turtleMap.containsKey(i)) {
        throw new InvalidTurtleException(
            String.format(errorMsgs.getString("UnknownTurtle"), "" + i));
      }
    }
    turtleIDStack.push(newActives);
    updateTurtleActives();
  }

  /**
   * Pops the turtle stack, returning to the previous active turtles
   */
  public void popTurtleStack() {
    turtleIDStack.pop();
    if (turtleIDStack.isEmpty()) {
      ArrayList<Integer> temp = new ArrayList<>();
      temp.add(1);
      turtleIDStack.push(temp);
      throw new StackUnderflowException(errorMsgs.getString("TurtleUnderflow"));
    }
    updateTurtleActives();
  }

  /**
   * Sets a new set of global active turtles
   * @param newActives
   */
  public void tellTurtleStack(List<Integer> newActives) {
    turtleIDStack.pop();
    for (int i : newActives) {
      if (turtleMap.getOrDefault(i, null) == null) {
        addTurtle(i);
      }
      setCurrentTurtle(i);
    }
    pushTurtleStack(newActives);
    updateTurtleActives();
  }

  private void updateTurtleActives() {
    for (Entry<Integer, Turtle> e : turtleMap.entrySet()) {
      e.getValue().setActive(turtleIDStack.peek().contains(e.getKey()));
      //set each turtle's active value to whether or not the top layer of the stack contains its id
    }
  }

  /**
   * Sets the future shape for all new turtles
   * @param shapeIndex the index of the future shape
   */
  public void setShapeIndex(int shapeIndex) {
    this.shapeIndex = shapeIndex;
  }

  /**
   * Sets the future pen color for all new turtles
   * @param penColorIndex the index of the new pen color
   */
  public void setPenColorIndex(int penColorIndex) {
    this.penColorIndex = penColorIndex;
  }

}
