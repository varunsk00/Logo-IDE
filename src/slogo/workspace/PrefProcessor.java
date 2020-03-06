package slogo.workspace;

import java.util.*;

public class PrefProcessor {
    final private static String DEFAULT_PREF_DIRMAP = "slogo.resources.preferences.default.path_map";
    final private static String DEFAULT_PREF_PATH = "slogo.resources.preferences.default.";
    final private static String USER_PREF_PATH = "slogo.resources.preferences.user.";

    private Workspace wspace;

    public PrefProcessor(){}

    public void initializeWorkspace(Workspace workspace, String prefKey){
        wspace = workspace;
    }

    public void saveWorkspace(Workspace workspace){}

    private void initializeBackgroundColor(){}

    private void initializePenColor(){}

    private void initializeTurtleNum(){}

    private void initializeTurtleShape(){}

    private void initializeTurtleImage(){}

    private void initializeLoadedFile(){}

    private void buildPrefMap(){}

    private void savePrefMap(){}

    private void saveCompilerCode(){}

    private List<Map.Entry<String, String>> loadDict(String resourcePath){
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }
}

BACKGROUND_COLOR = WHITE
        PEN_COLOR = BLACK
        IS_TURTLE_SHAPE = FALSE
        TURTLE_SHAPE = NA
        TURTLE_IMAGE = slogo/resources/images/turtle_green.png
        NUM_TURTLES = 1
        IS_LOAD_FROM_FILE = T
        PRELOAD_FILE = data/examples/recursion/spiral.logo