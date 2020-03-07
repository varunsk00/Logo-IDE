package slogo.compiler.parser.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.turtle.Turtle;

public class TurtleMemoryState {

  private Map<Integer, Turtle> turtleMap;
  private List<Integer> IDs;
  private int shape;
  private int pencolor;

  public TurtleMemoryState(Map<Integer, Turtle> t, List<Integer> id, int sh, int pc) {
    turtleMap = t;
    IDs = id;
    shape = sh;
    pencolor = pc;
  }

  public Map<Integer, Turtle> getTurtleMap() {
    return new HashMap<>(turtleMap);
  }

  public List<Integer> getIDs() {
    return new ArrayList<>(IDs);
  }

  public int getShape() {
    return shape;
  }

  public int getPenColor() {
    return pencolor;
  }
}
