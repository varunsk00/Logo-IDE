package slogo.variable_panels.subpanels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import slogo.variable_panels.util_classes.TableEntry;

import java.util.*;

public class AutoTableView extends TableView {
    private final static String LOCAL_RESOURCE_PATH = "slogo.variable_panels.local_resources.";
    private final static String CSS_FILEPATH = "slogo/resources/styleSheets/table_list_view.css";
    private final static double COLUMN_WIDTH = 200;

    private final static String COMMAND_PATH = ".command_col_titles";
    private final static String DEFINED_PATH = ".defined_col_titles";
    private final static String VAR_PATH = ".var_col_titles";

    private final static String COMMAND_TYPE = "COMMAND";
    private final static String DEFINED_TYPE = "DEFINED";
    private final static String VAR_TYPE = "VAR";

    private final static int colNum = 2;

    private double width;
    private double height;

    private String currentResourcePath;
    private ObservableList<TableEntry> data;
    private String type;
    private String currentLanguage;

    public AutoTableView(double width, double height, String type) {
        super();
        this.type = type;
        this.currentLanguage = "English";
        factoryType();
        initializeTable();
        setSize(width, height);
    }

    public void changeLanguageTo(String newLanguage){
        currentLanguage = newLanguage;
        updateTable();
    }

    private void initializeTable(){
        setEditable(false);
        List<Map.Entry<String, String>> colDict = loadDict();

        for (Map.Entry<String, String> col: colDict){
            initializeCol(col.getValue(), col.getKey(), col.getKey());
        }
        data = FXCollections.observableArrayList();
        setItems(data);

        getStylesheets().add(CSS_FILEPATH);
        setStyle("-fx-font-family: \"Consolas\";");
    }

    public void updateTable(){
        factoryType();
        List<Map.Entry<String, String>> newDict = loadDict();
        for (Object item: getColumns()){
            if (item instanceof TableColumn){
                TableColumn col = (TableColumn) item;
                col.setText(searchMapList(col.getId(), newDict));
            }
        }
    }

    public void addEntry(String key, String value){
        if (!hasEntryKey(key)) {
            data.add(new TableEntry(key, value));
        }
        else{
            for (TableEntry entry:data){
                if (key.equals(entry.getKey())){
                    entry.setValue(value);
                }
            }
        }
    }

    private String searchMapList(String key, List<Map.Entry<String, String>> dict){
        for(Map.Entry<String, String> entry: dict){
            if (key.equals(entry.getKey())) return entry.getValue();
        }
        return "";
    }

    private boolean hasEntryKey(String key){
        for (TableEntry entry:data){
            if (key.equals(entry.getKey())){
                return true;
            }
        }
        return false;
    }

    private void initializeCol(String colTitle, String colId, String cellFactory){
        TableColumn col  = new TableColumn(colTitle);
        setColSize(col, getColWidth());
        col.setCellValueFactory(new PropertyValueFactory<TableEntry, String>(cellFactory));
        col.setId(colId);
        getColumns().add(col);
    }

    private void setColSize(TableColumn col, double width){
        //col.setMinWidth(width);
        col.setPrefWidth(COLUMN_WIDTH);
    }

    private double getColWidth(){
        return width/colNum;
    }

    private void setSize(double width, double height){
        this.width = width;
        this.height = height;
    }

    private List<Map.Entry<String, String>> loadDict(){
        System.out.println(currentResourcePath);
        ResourceBundle resources = ResourceBundle.getBundle(currentResourcePath);
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }

    private void factoryType(){
        //System.out.println(type);
        //System.out.println(COMMAND_TYPE);
        switch (type){
            case COMMAND_TYPE:
                currentResourcePath = String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, COMMAND_PATH);
                break;
            case VAR_TYPE:
                currentResourcePath = String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, VAR_PATH);
                break;
            case DEFINED_TYPE:
                currentResourcePath = String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, DEFINED_PATH);
                break;
        }
    }

}
