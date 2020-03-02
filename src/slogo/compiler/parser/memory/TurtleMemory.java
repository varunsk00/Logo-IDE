package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import slogo.compiler.exceptions.InvalidTurtleException;
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
      throw new InvalidTurtleException(String.format(errorMsgs.getString("UnknownTurtle"), id));
    }
    return ret;
  }

  public Turtle getCurrentTurtle() {
    return getTurtleByID(currentTurtleID);
  }


  public Collection<Integer> getAllTurtleIDs() {
    return turtleMap.keySet();
  }

  //FIXME does this do a copy? should it?
  public Map<Integer, Turtle> getTurtleMapCopy() {
    return new HashMap<>(turtleMap);
  }

}
