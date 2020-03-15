package slogo.variable_panels;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import slogo.variable_panels.subpanels.AutoTableView;

/**
 * VariablesTabPaneView generates the TabPane object that contains the tables for command
 * history, variable explore, and user-defined methods.
 *
 * @author Qiaoyi Fang
 */
public class VariablesTabPaneView extends TabPane {

  private final static String LOCAL_RESOURCE_PATH = "slogo.variable_panels.local_resources.";
  private final static String TAB_NAMES = ".tab_titles";
  private final static String CSS_FILEPATH = "slogo/resources/styleSheets/variable_table.css";

  private String currentLanguage;
  private List<Map.Entry<String, AutoTableView>> tableDict;

  /**
   * Constructor
   *
   * @param width  width
   * @param height height
   */
  public VariablesTabPaneView(double width, double height) {
    setSize(width, height);
    initializeTabPane();
  }

  /**
   * Adds the entry to one table
   *
   * @param type  table type ("DEFINED", "COMMAND", "VAR")
   * @param key   key entry
   * @param value value entry
   * @param isKey whether the key axis is the input area
   */
  public void addEntry(String type, String key, String value, Boolean isKey) {
    Objects.requireNonNull(getEntry(tableDict, type)).addEntry(key, value, isKey);
  }

  /**
   * Clears the table for one type
   *
   * @param type table type ("DEFINED", "COMMAND", "VAR")
   */
  public void clearAll(String type) {
    Objects.requireNonNull(getEntry(tableDict, type)).clearAll();
  }

  /**
   * Changes the size of tabpane
   *
   * @param width  width
   * @param height height
   */
  public void setSize(double width, double height) {
    setPrefSize(width, height);
  }

  /**
   * Changes the language
   *
   * @param language language
   */
  public void changeLanguageTo(String language) {
    currentLanguage = language;
    updateTabPane();
    updateTabs();
  }

  /**
   * Returns the dictionary of tables
   *
   * @return List<Map.Entry <>> object
   */
  public List<Map.Entry<String, AutoTableView>> getTableDict() {
    return tableDict;
  }

  private void updateTabs() {
    for (Tab tab : getTabs()) {
      if (tab.getContent() instanceof AutoTableView) {
        AutoTableView table = (AutoTableView) tab.getContent();
        table.changeLanguageTo(currentLanguage);
      }
    }
  }

  private void initializeTabPane() {
    currentLanguage = "English";
    tableDict = new ArrayList<>();
    List<Map.Entry<String, String>> tabDict = loadTabNameDict();
    for (Map.Entry<String, String> tab : tabDict) {
      AutoTableView newTable = new AutoTableView(tab.getKey());
      initializeTab(tab.getValue(), tab.getKey(), newTable);
      tableDict.add(new AbstractMap.SimpleEntry<>(tab.getKey(), newTable));
    }
    getStylesheets().add(CSS_FILEPATH);
    setStyle("-fx-font-family: \"Consolas\";");
  }

  private void updateTabPane() {
    List<Map.Entry<String, String>> newDict = loadTabNameDict();
    for (Tab tab : getTabs()) {
      tab.setText(searchMapList(tab.getId(), newDict));
    }
  }

  private String searchMapList(String key, List<Map.Entry<String, String>> dict) {
    for (Map.Entry<String, String> entry : dict) {
      if (key.equals(entry.getKey())) {
        return entry.getValue();
      }
    }
    return "";
  }

  private void initializeTab(String tabTitle, String tabId, Node tabContent) {
    Tab newTab = new Tab(tabTitle);
    newTab.setId(tabId);
    newTab.setContent(tabContent);
    getTabs().add(newTab);
  }

  private List<Map.Entry<String, String>> loadTabNameDict() {
    ResourceBundle resources = ResourceBundle.getBundle(getResourceAddress());
    List<Map.Entry<String, String>> dict = new ArrayList<>();
    for (String tabType : Collections.list(resources.getKeys())) {
      String tabName = resources.getString(tabType);
      dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
    }
    return dict;
  }

  private String getResourceAddress() {
    return String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, TAB_NAMES);
  }

  private AutoTableView getEntry(List<Map.Entry<String, AutoTableView>> dict, String key) {
    for (Map.Entry<String, AutoTableView> item : dict) {
      if (item.getKey().equals(key)) {
        return item.getValue();
      }
    }
    return null;
  }

}
