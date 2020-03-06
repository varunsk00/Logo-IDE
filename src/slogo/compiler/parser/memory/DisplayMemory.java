package slogo.compiler.parser.memory;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DisplayMemory {

  private Map<Integer, int[]> palette;
  private int backgroundColor;
  private ArrayDeque<DisplayMemoryState> historyStack = new ArrayDeque<>();

  public DisplayMemory() {
    palette = new HashMap<>();
    backgroundColor = 0;
  }

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

  public void undo() {
    if (!historyStack.isEmpty()) {
      DisplayMemoryState state = historyStack.pop();
      palette = state.getPal();
      backgroundColor = state.getBackground();
    }
  }

  public int getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void addColor(int idx, int r, int g, int b) {
    palette.put(idx, new int[]{r, g, b});
  }

  public Map<Integer, int[]> getPaletteColors() {
    Map<Integer, int[]> ret = new HashMap<>();
    for (Entry<Integer, int[]> e : palette.entrySet()) {
      int[] rgb = e.getValue();
      ret.put(e.getKey(), new int[]{rgb[0], rgb[1], rgb[2]});
    }
    return ret;
  }
}
