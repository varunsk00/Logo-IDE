package slogo.workspace;

import javafx.scene.paint.Color;

import java.util.*;

public class ColorFactory {
    private List<Map.Entry<String, Color>> myColorMap;
    private static final String RESOURCES_DIRECTORY = "slogo.resources.preferences.Colors";
    public ColorFactory(){
        myColorMap = loadDict(RESOURCES_DIRECTORY);
    }

    public Color parseColor(int id){
        for (Map.Entry<String, Color> color: myColorMap){
            if (color.getKey().equals(String.valueOf(id))){
                return color.getValue();
            }
        }
        return Color.SKYBLUE;
    }

    private List<Map.Entry<String, Color>> loadDict(String resourcePath){
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
        List<Map.Entry<String, Color>> dict = new ArrayList<>();
        for (String colorID: Collections.list(resources.getKeys())){
            String hexCode = resources.getString(colorID);
            dict.add(new AbstractMap.SimpleEntry<>(colorID, Color.valueOf(hexCode)));
        }
        return dict;
    }
}
