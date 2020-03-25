package slogo.workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

public class PrefProcessor {

  final private static String DEFAULT_PREF_DIRMAP = "slogo.resources.preferences.default.path_map";
  final private static String DEFAULT_PREF_PATH = "slogo.resources.preferences.default.";
  final private static String USER_PREF_PATH = "src/slogo/resources/preferences/user/";

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
  List<Map.Entry<String, String>> prefDict;
  private Workspace wspace;
  private ColorFactory colorFactory;
  private boolean isInitializedShapeImage;
  int saveCnt = 0;

  public PrefProcessor() {
    prefDict = new LinkedList<>();
    colorFactory = new ColorFactory();
    isInitializedShapeImage = false;
  }

  public void initializeWorkspace(Workspace workspace, String prefKey)
      throws FileNotFoundException {
    wspace = workspace;
    List<Map.Entry<String, String>> pathMap = loadDict(DEFAULT_PREF_DIRMAP);

    boolean isFound = false;

    String defaultUnfoundPrefPath = "";
    for (Map.Entry<String, String> entry : pathMap) {
      defaultUnfoundPrefPath = entry.getValue();
      if (entry.getKey().equals(prefKey)) {
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

  public void loadPref(Workspace workspace, String filePath) throws FileNotFoundException {
    this.wspace = workspace;
    prefDict = loadDict(filePath);
    loadPrefDict();
  }

  private void loadPrefDict() throws FileNotFoundException {
    initializeBackgroundColor();
    initializePenColor();
    initializeTurtleNum();
    initializeTurtleImageShape();
    initializeLoadedFile();
    initializeClearScreen();
  }

  public void initializeClearScreen(){
    wspace.getTerminalController().sendInput(generateClearScreen());
  }

  private void initializeTurtleImageShape() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(IS_TURTLE_SHAPE_KEY) && entry.getValue().equals(TRUE_VAL)) {
        initializeTurtleShape();
      } else if (entry.getKey().equals(IS_TURTLE_SHAPE_KEY) && entry.getValue().equals(FALSE_VAL)) {
        initializeTurtleImage();
      }
    }
  }

  private void initializeBackgroundColor() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(BACKGROUND_COLOR_KEY)) {
        wspace.getTerminalController().sendInput(generateBackgroundCommand(entry.getValue()));
        return;
      }
    }
  }

  private void initializePenColor() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(TURTLE_NUM_KEY)) {
        wspace.getTerminalController().sendInput(generatePenCommand(entry.getValue()));
        return;
      }
    }
  }

  private void initializeTurtleNum() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(TURTLE_NUM_KEY)) {
        wspace.getTerminalController()
            .sendInput(generateTurtleCommand(Integer.parseInt(entry.getValue())));
        return;
      }
    }
  }

  private void initializeTurtleShape() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(TURTLE_SHAPE_KEY)) {
        wspace.getHabitat().updateAllTurtlesShapeColor(Integer.parseInt(entry.getValue()));
      }
    }
  }

  public void updateShapeImage() {
    if (!isInitializedShapeImage) {
      isInitializedShapeImage = true;
      initializeTurtleImageShape();
    }
  }

  private void initializeTurtleImage() {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(TURTLE_IMAGE_KEY)) {
        wspace.getHabitat().updateAllTurtlesImage(entry.getValue());
      }
    }
  }

  private void initializeLoadedFile() throws FileNotFoundException {
    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(IS_LOAD_FILE_KEY) && entry.getValue().equals(FALSE_VAL)) {
        return;
      }
    }

    for (Map.Entry<String, String> entry : prefDict) {
      if (entry.getKey().equals(PRELOAD_FILE_KEY)) {
        wspace.getTerminalController().sendFileInput(new File(entry.getValue()));
      }
    }
  }

  public String buildPrefMap() throws IOException {
    StringBuffer text = new StringBuffer();
    String colorID = colorFactory.parseColor(wspace.getHabitat().getBackgroundColor());
    text.append(generatePropertiesEntry(BACKGROUND_COLOR_KEY, colorID));

    return savePrefMap(text.toString());
  }

  private String savePrefMap(String text) throws IOException {
    String filename = String.format("%d.properties", ++saveCnt);
    String filepath = String.format("%s%s", USER_PREF_PATH, filename);

    File newOuputFile = new File(filepath);
    if (newOuputFile.createNewFile()){
      System.out.println(String.format("%s File created.", filename));
    }
    else {
      System.out.println(String.format("%s already exists.", filename));
    }
    FileWriter fileWriter = new FileWriter(newOuputFile);
    fileWriter.write(text);
    fileWriter.close();
    return filename;
  }

  private List<Map.Entry<String, String>> loadDict(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    List<Map.Entry<String, String>> dict = new ArrayList<>();
    for (String tabType : Collections.list(resources.getKeys())) {
      String tabName = resources.getString(tabType);
      dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
    }
    return dict;
  }

  private String generateBackgroundCommand(String colorID) {
    return String.format("setbg %s", colorID);
  }

  private String generatePenCommand(String colorID) {
    return String.format("setpc %s", colorID);
  }

  private String generateTurtleCommand(int num) {
    String command = "tell [ ";
    for (int i = 1; i <= num; i++) {
      command = String.format("%s %s", command, i);
    }
    command = String.format("%s]", command);
    return command;
  }

  private String getFilePath(String relativeFilePath) {
    return String.format("%s%s", DEFAULT_PREF_PATH, relativeFilePath);
  }

  private String generateClearScreen(){return "cs";}

  private String generatePropertiesEntry(String key, String val){return String.format("%s=%s\n", key, val);}
}
