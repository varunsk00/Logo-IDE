package slogo.compiler.parser.memory;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

public class DisplayMemory {
  private Map<Integer, int[]> palette;
  private int backgroundColor;

  public DisplayMemory() {
    palette = new HashMap<>();
    backgroundColor = 0;
  }

  public int getBackgroundColor() {
    return backgroundColor;
  }

  public void setBackgroundColor(int backgroundColor) {
    this.backgroundColor = backgroundColor;
  }

  public void addColor(int idx, int r, int g, int b) {
    palette.put(idx, new int[] {r,g,b});
  }

  public Map<Integer, int[]> getPaletteColors() {
    Map<Integer, int[]> ret = new HashMap<>();
    for (Entry<Integer, int[]> e: palette.entrySet()) {
      int[] rgb = e.getValue();
      ret.put(e.getKey(),new int[] {rgb[0], rgb[1], rgb[2]});
    }
    return ret;
  }
}
