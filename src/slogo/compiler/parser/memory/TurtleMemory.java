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

public class TurtleMemory {

  private ResourceBundle errorMsgs;
  private Map<Integer, Turtle> turtleMap = new HashMap<>();
  private ArrayDeque<List<Integer>> turtleIDStack = new ArrayDeque<>();
  private ArrayDeque<TurtleMemoryState> historyStack = new ArrayDeque<>();
  private int currentTurtleID;

  public TurtleMemory() {
    List<Integer> startIDs = new ArrayList<>();
    startIDs.add(1);
    turtleIDStack.push(startIDs);
    addTurtle(1);
  }

  public void save() {
    Map<Integer, Turtle> tur = new HashMap<>();
    for (Entry<Integer, Turtle> e : turtleMap.entrySet()) {
      tur.put(e.getKey(), new Turtle(e.getValue()));
    }
    List<Integer> ids = new ArrayList<>(turtleIDStack.getLast());
    historyStack.push(new TurtleMemoryState(tur, ids));
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  public void undo() {
    if (!historyStack.isEmpty()) {
      TurtleMemoryState state = historyStack.pop();
      turtleMap = state.getTurtleMap();
      turtleIDStack.removeLast();
      turtleIDStack.addLast(state.getIDs());
      for (int i : getAllTurtleIDs()) {
        getTurtleByID(i).resetLocation();
        setCurrentTurtle(i);
      }
    }
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  public void addTurtle(int id) {
    addTurtle(id, new Turtle(id));
  }

  public void addTurtle(int id, Turtle t) {
    turtleMap.put(id, t);
    currentTurtleID = id;
  }

  public Turtle getTurtleByID(int id) {
    Turtle ret = turtleMap.getOrDefault(id, null);
    if (ret == null) {
      throw new InvalidTurtleException(
          String.format(errorMsgs.getString("UnknownTurtle"), "" + id));
    }
    return ret;
  }

  public Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }

  public void setCurrentTurtle(int id) {
    currentTurtleID = id;
  }

  public int getCurrentTurtleID() {
    return currentTurtleID;
  }

  public Collection<Integer> getAllTurtleIDs() {
    return turtleMap.keySet();
  }

  public List<Integer> getActiveTurtleIDs() {
    return new ArrayList<>(turtleIDStack.peek());
  }

  public void toggleActiveTurtle(int id) {
    List<Integer> actives = getActiveTurtleIDs();
    if (actives.contains(id)) {
      actives.remove(Integer.valueOf(id));
    } else {
      actives.add(id);
    }
    tellTurtleStack(actives);
  }

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

  //FIXME does this do a copy? should it?
  public Map<Integer, Turtle> getTurtleMapCopy() {
    return new HashMap<>(turtleMap);
  }

}
