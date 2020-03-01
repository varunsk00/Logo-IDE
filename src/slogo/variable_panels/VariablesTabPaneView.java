package slogo.variable_panels;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import slogo.terminal.utils.textLines.TestLine;
import slogo.variable_panels.subpanels.AutoTableView;
import slogo.variable_panels.util_classes.TableEntry;

import java.util.*;


public class VariablesTabPaneView extends TabPane {
    private final static String LOCAL_RESOURCE_TAB_NAMES = String.format("slogo.variable_panels.local_resources.tab_titles", TestLine.class.getPackageName());
    private final static String CSS_FILEPATH = "slogo/resources/styleSheets/variable_table.css";

    private double width;
    private double height;

    private Map<String, AutoTableView> tableDict;

    public VariablesTabPaneView(double width, double height){
        setSize(width, height);
        initializeTabPane();
    }

    public void addEntry(String type, String key, String value){
        tableDict.get(type).addEntry(key, value);
    }

    public void setSize(double width, double height){
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
    }

    private void initializeTabPane(){
        tableDict = new HashMap<>();
        List<Map.Entry<String, String>> tabDict = loadTabNameDict();
        for (Map.Entry<String, String> tab: tabDict){
            AutoTableView newTable = new AutoTableView(getTabWidth(), getTabHeight(), tab.getKey());
            initializeTab(tab.getValue(), newTable);
            tableDict.put(tab.getKey(), newTable);
        }
        getStylesheets().add(CSS_FILEPATH);
        setStyle("-fx-font-family: \"Consolas\";");
    }

    private void initializeTab(String tabTitle, Node tabContent){
        Tab newTab = new Tab(tabTitle);
        newTab.setContent(tabContent);
        getTabs().add(newTab);
    }

    private List<Map.Entry<String, String>> loadTabNameDict(){
        ResourceBundle resources = ResourceBundle.getBundle(LOCAL_RESOURCE_TAB_NAMES);
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }

    //TODO: what is the size of tabs?
    private double getTabWidth(){
        return width;
    }

    private double getTabHeight(){
        return height;
    }

}
