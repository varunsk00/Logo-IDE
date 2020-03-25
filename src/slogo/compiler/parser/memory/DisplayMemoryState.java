package slogo.compiler.parser.memory;

import java.util.Map;

/**
 * @author Maverick Chung mc608
 *
 * Purpose: Store a state of the DisplayMemory such that it can later be reverted to
 */
public class DisplayMemoryState {

  private int background;
  private Map<Integer, int[]> pal;

  public DisplayMemoryState(int b, Map<Integer, int[]> p) {
    pal = p;
    background = b;
  }

  /**
   * Returns stored background index
   * @return stored background index
   */
  public int getBackground() {
    return background;
  }

  /**
   * Returns stored palette
   * @return stored palette
   */
  public Map<Integer, int[]> getPal() {
    return pal;
  }
}
