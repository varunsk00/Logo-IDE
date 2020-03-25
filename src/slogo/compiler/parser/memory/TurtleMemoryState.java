package slogo.compiler.parser.memory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import slogo.turtle.Turtle;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Store a state of the TurtleMemory such that it can later be reverted to
 */
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

  /**
   * Returns stored turtles
   *
   * @return stored turtles
   */
  public Map<Integer, Turtle> getTurtleMap() {
    return new HashMap<>(turtleMap);
  }

  /**
   * Returns stored IDs
   *
   * @return stored IDs
   */
  public List<Integer> getIDs() {
    return new ArrayList<>(IDs);
  }

  /**
   * Returns stored shape index
   *
   * @return stored shape index
   */
  public int getShape() {
    return shape;
  }

  /**
   * Returns stored pen color index
   *
   * @return stored pen color index
   */
  public int getPenColor() {
    return pencolor;
  }
}
