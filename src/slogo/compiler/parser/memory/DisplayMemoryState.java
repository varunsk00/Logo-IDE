package slogo.compiler.parser.memory;

import java.util.Map;

public class DisplayMemoryState {

  private int background;
  private Map<Integer, int[]> pal;

  public DisplayMemoryState(int b, Map<Integer, int[]> p) {
    pal = p;
    background = b;
  }

  public int getBackground() {
    return background;
  }

  public Map<Integer, int[]> getPal() {
    return pal;
  }
}
