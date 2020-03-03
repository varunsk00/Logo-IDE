package slogo.variable_panels;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import slogo.variable_panels.subpanels.AutoTableView;

import java.util.*;


public class VariablesTabPaneView extends TabPane {
    private final static String LOCAL_RESOURCE_PATH = "slogo.variable_panels.local_resources.";
    private final static String TAB_NAMES = ".tab_titles";
    private final static String CSS_FILEPATH = "slogo/resources/styleSheets/variable_table.css";

    private double width;
    private double height;

    private String currentLanguage;

    private List<Map.Entry<String, AutoTableView>> tableDict;

    public VariablesTabPaneView(double width, double height){
        setSize(width, height);
        initializeTabPane();
    }

    public void addEntry(String type, String key, String value, Boolean isKey){
        getEntry(tableDict, type).addEntry(key, value, isKey);
    }

    public void clearAll(String type){
        getEntry(tableDict, type).clearAll();
    }

    public void setSize(double width, double height){
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
    }

    public void changeLanguageTo(String language){
        currentLanguage = language;
        updateTabPane();
        updateTabs();
    }

    public List<Map.Entry<String, AutoTableView>> getTableDict(){
        return tableDict;
    }

    private void updateTabs(){
        for (Tab tab: getTabs()){
            if (tab.getContent() instanceof AutoTableView){
                AutoTableView table = (AutoTableView) tab.getContent();
                table.changeLanguageTo(currentLanguage);
            }
        }
    }

    private void initializeTabPane(){
        currentLanguage = "English";
        tableDict = new ArrayList<>();
        List<Map.Entry<String, String>> tabDict = loadTabNameDict();
        for (Map.Entry<String, String> tab: tabDict){
            AutoTableView newTable = new AutoTableView(getTabWidth(), getTabHeight(), tab.getKey());
            initializeTab(tab.getValue(), tab.getKey(), newTable);
            tableDict.add(new AbstractMap.SimpleEntry<>(tab.getKey(), newTable));
        }
        getStylesheets().add(CSS_FILEPATH);
        setStyle("-fx-font-family: \"Consolas\";");
    }

    private void updateTabPane(){
        List<Map.Entry<String, String>> newDict = loadTabNameDict();
        for (Tab tab: getTabs()){
            tab.setText(searchMapList(tab.getId(), newDict));
        }
    }

    private String searchMapList(String key, List<Map.Entry<String, String>> dict){
        for(Map.Entry<String, String> entry: dict){
            if (key.equals(entry.getKey())) return entry.getValue();
        }
        return "";
    }

    private void initializeTab(String tabTitle, String tabId, Node tabContent){
        Tab newTab = new Tab(tabTitle);
        newTab.setId(tabId);
        newTab.setContent(tabContent);
        getTabs().add(newTab);
    }

    private List<Map.Entry<String, String>> loadTabNameDict(){
        ResourceBundle resources = ResourceBundle.getBundle(getResourceAddress());
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }

    private String getResourceAddress(){
        System.out.println(String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, TAB_NAMES));
        return String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, TAB_NAMES);
    }

    private AutoTableView getEntry(List<Map.Entry<String, AutoTableView>> dict, String key){
        for (Map.Entry<String, AutoTableView> item: dict){
            if (item.getKey().equals(key)){
                return item.getValue();
            }
        }
        return null;
    }

    //TODO: what is the size of tabs?
    private double getTabWidth(){
        return width;
    }

    private double getTabHeight(){
        return height;
    }

}
