package slogo.workspace;

import javafx.scene.paint.Color;

import java.util.*;

public class PrefProcessor {
    final private static String DEFAULT_PREF_DIRMAP = "slogo.resources.preferences.default.path_map";
    final private static String DEFAULT_PREF_PATH = "slogo.resources.preferences.default.";
    final private static String USER_PREF_PATH = "slogo.resources.preferences.user.";

    final private static String BACKGROUND_COLOR_KEY = "BACKGROUND_COLOR";
    final private static String PEN_COLOR_KEY = "PEN_COLOR";
    final private static String IS_TURTLE_SHAPE_KEY = "IS_TURTLE_SHAPE";
    final private static String TURTLE_SHAPE_KEY = "TURTLE_SHAPE";
    final private static String TURTLE_IMAGE_KEY = "TURTLE_IMAGE";
    final private static String TURTLE_NUM_KEY = "NUM_TURTLES";
    final private static String IS_LOAD_FILE_KEY = "IS_LOAD_FROM_FILE";
    final private static String PRELOAD_FILE_KEY = "PRELOAD_FILE";

    final private static String TRUE_VAL = "T";
    final private static String FALSE_VAL = "F";
    final private static String NA_VAL = "NA";

    private Workspace wspace;
    private ColorFactory colorFactory;
    List<Map.Entry<String, String>> prefDict;

    public PrefProcessor(){prefDict = new LinkedList<>(); colorFactory = new ColorFactory();}

    public void initializeWorkspace(Workspace workspace, String prefKey){
        wspace = workspace;
        List<Map.Entry<String, String>> pathMap = loadDict(DEFAULT_PREF_DIRMAP);

        boolean isFound = false;

        String defaultUnfoundPrefPath = "";
        for (Map.Entry<String, String> entry: pathMap){
            defaultUnfoundPrefPath = entry.getValue();
            if (entry.getKey().equals(prefKey)){
                isFound = true;
                prefDict = loadDict(String.format("%s%s", DEFAULT_PREF_PATH, entry.getValue()));
            }
        }
        if (!isFound) {
            System.out.println("Error: unfounded keyword for the preference resource file");
            prefDict = loadDict(String.format("%s%s", DEFAULT_PREF_PATH, defaultUnfoundPrefPath));
        }

        loadPrefDict();

    }

    public void loadPref(Workspace workspace, String filePath){
        this.wspace = workspace;
        prefDict = loadDict(filePath);
        loadPrefDict();
    }

    private void loadPrefDict(){
        initializeBackgroundColor();
        initializePenColor();
        initializeTurtleNum();
        initializeTurtleImageShape();
        initializeLoadedFile();
    }

    public void saveWorkspace(Workspace workspace){}

    private void initializeTurtleImageShape(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(IS_TURTLE_SHAPE_KEY) && entry.getValue().equals(TRUE_VAL)){ initializeTurtleShape(); }
            else if (entry.getKey().equals(IS_TURTLE_SHAPE_KEY) && entry.getValue().equals(FALSE_VAL)){ initializeTurtleImage(); }
        }
    }

    private void initializeBackgroundColor(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(BACKGROUND_COLOR_KEY)){
                Color newColor = colorFactory.parseColor(Integer.parseInt(entry.getValue()));
                wspace.getHabitat().setBackground(newColor);
                wspace.setDefaultBackgroundColor(newColor);
            }
        }
    }

    private void initializePenColor(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(PEN_COLOR_KEY)){
                Color newColor = colorFactory.parseColor(Integer.parseInt(entry.getValue()));
                wspace.setDefaultPenColorColor(newColor);
                wspace.getHabitat().setAllTurtlesPenColor(newColor);
            }
        }
    }

    private void initializeTurtleNum(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(TURTLE_NUM_KEY)){
                //
            }
        }
    }

    private void initializeTurtleShape(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(TURTLE_SHAPE_KEY)){
                //
            }
        }
    }

    private void initializeTurtleImage(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(TURTLE_IMAGE_KEY)){
                //
            }
        }
    }

    private void initializeLoadedFile(){
        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(IS_LOAD_FILE_KEY) && entry.getValue().equals(FALSE_VAL)){
                return;
            }
        }

        for (Map.Entry<String, String> entry : prefDict){
            if (entry.getKey().equals(PRELOAD_FILE_KEY)){

            }
        }
    }

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
