package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

/**
 * @author Maverick Chung mc608
 * <p>
 * Purpose: Stores background color index and palette
 * <p>
 * Assumptions: All inputs are valid and verified elsewhere, as with Memory
 * <p>
 * Dependencies: Memory, DisplayMemoryState
 */
public class DisplayMemory {

  private Map<Integer, int[]> palette;
  private int backgroundColor;
  private ArrayDeque<DisplayMemoryState> historyStack = new ArrayDeque<>();

  /**
   * Creates new DisplayMemory
   */
  public DisplayMemory() {
    palette = new HashMap<>();
    backgroundColor = 0;
  }

  /**
   * Saves the state of memory such that it can be reverted to
   */
  public void save() {
    Map<Integer, int[]> pal = new HashMap<>();
    for (Entry<Integer, int[]> e : palette.entrySet()) {
      int[] col = e.getValue();
      col = new int[]{col[0], col[1], col[2]};
      pal.put(e.getKey(), col);
    }
    historyStack.push(new DisplayMemoryState(backgroundColor, pal));
    while (historyStack.size() > Memory.MAX_HISTORY_STORED) {
      historyStack.removeLast();
    }
  }

  /**
   * Reverts to most recent saved state, throwing away current state
   */
  public void undo() {
    if (!historyStack.isEmpty()) {
      DisplayMemoryState state = historyStack.pop();
      palette = state.getPal();
      backgroundColor = state.getBackground();
    }
  }

  /**
   * Returns the id of the current background color
   *
   * @return the id of the current background color
   */
  public int getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Sets the id of the current background color
   *
   * @param backgroundColor the id to be set
   */
  public void setBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  /**
   * Adds a new RGB color to the palette
   *
   * @param idx the index of the color
   * @param r   the red value
   * @param g   the green value
   * @param b   the blue value
   */
  public void addColor(int idx, int r, int g, int b) {
    palette.put(idx, new int[]{r, g, b});
  }

  /**
   * Gets a map mapping the IDs to the [r,g,b] colors in the current palette
   *
   * @return
   */
  public Map<Integer, int[]> getPaletteColors() {
    Map<Integer, int[]> ret = new HashMap<>();
    for (Entry<Integer, int[]> e : palette.entrySet()) {
      int[] rgb = e.getValue();
      ret.put(e.getKey(), new int[]{rgb[0], rgb[1], rgb[2]});
    }
    return ret;
  }
}
