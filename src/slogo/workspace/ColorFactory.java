package slogo.workspace;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.paint.Color;

public class ColorFactory {

  private static final String RESOURCES_DIRECTORY = "slogo.resources.preferences.Colors";
  private List<Map.Entry<String, Color>> myColorMap;

  public ColorFactory() {
    myColorMap = loadDict(RESOURCES_DIRECTORY);
  }

  public Color parseColor(int id) {
    for (Map.Entry<String, Color> color : myColorMap) {
      if (color.getKey().equals(String.valueOf(id))) {
        return color.getValue();
      }
    }
    return Color.SKYBLUE;
  }

  private List<Map.Entry<String, Color>> loadDict(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    List<Map.Entry<String, Color>> dict = new ArrayList<>();
    for (String colorID : Collections.list(resources.getKeys())) {
      String hexCode = resources.getString(colorID);
      dict.add(new AbstractMap.SimpleEntry<>(colorID, Color.valueOf(hexCode)));
    }
    return dict;
  }
}
