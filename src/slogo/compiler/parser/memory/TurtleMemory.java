package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidTurtleException;
import slogo.compiler.exceptions.StackUnderflowException;
import slogo.turtle.Turtle;

public class TurtleMemory {

  private ResourceBundle errorMsgs;
  private Map<Integer, Turtle> turtleMap = new HashMap<>();
  private ArrayDeque<List<Integer>> turtleIDStack = new ArrayDeque<>();
  private int currentTurtleID;

  public TurtleMemory() {
    List<Integer> startIDs = new ArrayList<>();
    startIDs.add(1);
    turtleIDStack.push(startIDs);
    addTurtle(1, new Turtle());
  }

  public void setErrorMsgs(ResourceBundle msgs) {
    errorMsgs = msgs;
  }

  public void addTurtle(int id, Turtle t) {
    turtleMap.put(id, t);
    currentTurtleID = id;
  }

  public Turtle getTurtleByID(int id) {
    Turtle ret = turtleMap.getOrDefault(id, null);
    if (ret == null) {
      throw new InvalidTurtleException(String.format(errorMsgs.getString("UnknownTurtle"), ""+id));
    }
    return ret;
  }

  public Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }

  public void setCurrentTurtle(int id) {
    currentTurtleID = id;
  }

  public Collection<Integer> getAllTurtleIDs() {
    return turtleMap.keySet();
  }

  public List<Integer> getActiveTurtleIDs(){
    return new ArrayList<>(turtleIDStack.peek());
  }

  public void pushTurtleStack(List<Integer> newActives)  {
    turtleIDStack.push(newActives);
  }

  public void popTurtleStack() {
    turtleIDStack.pop();
    if (turtleIDStack.isEmpty()) {
      ArrayList<Integer> temp = new ArrayList<>();
      temp.add(1);
      turtleIDStack.push(temp);
      throw new StackUnderflowException(errorMsgs.getString("TurtleUnderflow"));
    }
  }

  public void tellTurtleStack(List<Integer> newActives) {
    turtleIDStack.pop();
    pushTurtleStack(newActives);
  }

  //FIXME does this do a copy? should it?
  public Map<Integer, Turtle> getTurtleMapCopy() {
    return new HashMap<>(turtleMap);
  }

}
