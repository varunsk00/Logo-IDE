package slogo.workspace;


import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

public class ColorFactory {

  private static final String RESOURCES_DIRECTORY = "slogo.resources.preferences.Colors";

  private Map<String, Color> myColorMap;
  private String UNMATCHED_COLOR_CODE = "-1";

  public ColorFactory() {
    myColorMap = loadDict(RESOURCES_DIRECTORY);
  }

  public Color parseColor(int id) {
    for (Entry<String, Color> color : myColorMap.entrySet()) {
      if (color.getKey().equals(String.valueOf(id))) {
        return color.getValue();
      }
    }
    return Color.SKYBLUE;
  }

  public String parseColor(Color c) {
    for (Entry<String, Color> color : myColorMap.entrySet()) {
      if (color.getValue().equals(c)) {
        return color.getKey();
      }
    }
    return UNMATCHED_COLOR_CODE;
  }

  public void addColor(int idx, Color c) {
    myColorMap.put(""+idx, c);
  }

  public int addColor(Color c) {
    int i = 1;
    while (true) {
      if (myColorMap.getOrDefault(""+i, null) == null ) {
        myColorMap.put(""+i, c);
        System.out.println(i);
        return i;
      }
      i++;
    }
  }

  private Map<String, Color> loadDict(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    Map<String, Color> dict = new HashMap<>();
    for (String colorID : Collections.list(resources.getKeys())) {
      String hexCode = resources.getString(colorID);
      dict.put(colorID, Color.valueOf(hexCode));
    }
    return dict;
  }
}
