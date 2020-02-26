package slogo.variable_panels;

import javafx.scene.Node;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import slogo.terminal.utils.textLines.TestLine;
import slogo.variable_panels.subpanels.commands.CommandListView;
import slogo.variable_panels.subpanels.userdefined.UserDefinedListView;
import slogo.variable_panels.subpanels.variables.VariableListView;

import java.util.*;


public class VariablesTabPaneView extends TabPane {
    private final static String LOCAL_RESOURCE_TAB_NAMES = String.format("%s.local_resources.tab_names", TestLine.class.getPackageName());
    private final static String VARIABLE_TYPE = "var";
    private final static String COMMAND_TYPE = "command";
    private final static String DEFINED_TYPE = "defined";

    private double width;
    private double height;

    public VariablesTabPaneView(double width, double height){
        setSize(width, height);
        initializeTabPane();
    }

    public void setSize(double width, double height){
        this.width = width;
        this.height = height;
        setPrefSize(width, height);
    }

    private void initializeTabPane(){
        List<Map.Entry<String, String>> tabDict = loadTabNameDict();
        for (Map.Entry<String, String> tab: tabDict){
            switch (tab.getKey()){
                case VARIABLE_TYPE:
                    initializeTab(tab.getValue(), new VariableListView(getTabWidth(), getTabHeight()));
                case COMMAND_TYPE:
                    initializeTab(tab.getValue(), new CommandListView(getTabWidth(), getTabHeight()));
                case DEFINED_TYPE:
                    initializeTab(tab.getValue(), new UserDefinedListView(getTabWidth(), getTabHeight()));
            }
        }
    }

    private void initializeTab(String tabTitle, Node tabContent){
        Tab newTab = new Tab(tabTitle);
        newTab.setContent(tabContent);
        getTabs().add(newTab);
    }

    //TODO: what is the size of tabs?
    private double getTabWidth(){
        return width;
    }

    private double getTabHeight(){
        return height;
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

}
