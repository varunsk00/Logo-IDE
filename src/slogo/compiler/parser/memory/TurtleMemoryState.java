package slogo.compiler.parser.memory;

import java.util.List;
import java.util.Map;
import slogo.turtle.Turtle;

public class TurtleMemoryState {
  private Map<Integer, Turtle> turtleMap;
  private List<Integer> IDs;

  public TurtleMemoryState(Map<Integer,Turtle> t, List<Integer> id) {
    turtleMap = t;
    IDs = id;
  }

  public Map<Integer, Turtle> getTurtleMap() {
    return turtleMap;
  }

  public List<Integer> getIDs() {
    return IDs;
  }
}
