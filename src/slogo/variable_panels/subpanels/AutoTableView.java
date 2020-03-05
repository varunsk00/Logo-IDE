package slogo.variable_panels.subpanels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.control.cell.TextFieldTableCell;
import slogo.variable_panels.util_classes.TableEntry;

import java.util.*;

/**
 * Generates the TableView object that automatically generates the content dependent on the table type
 * (command history, defined methods, variable).
 */
public class AutoTableView extends TableView {
    private final static String LOCAL_RESOURCE_PATH = "slogo.variable_panels.local_resources.";
    private final static String LOCAL_RESOURCE_FILEPATH = "slogo.variable_panels.local_resources.filepath";
    private final static String CSS_FILEPATH = "slogo/resources/styleSheets/table_list_view.css";
    private final static double COLUMN_WIDTH = 200;

    private final static String KEY_MATCH_TERM = "key";

    private double width;
    private double height;

    private String currentResourcePath;
    private ObservableList<TableEntry> data;
    private String type;
    private String currentLanguage;

    /**
     * Constructor
     * @param width width
     * @param height height
     * @param type table type ("COMMAND", "DEFINED", "VAR" from resource file)
     */
    public AutoTableView(double width, double height, String type) {
        super();
        this.type = type;
        this.currentLanguage = "English";
        initializeTable();
        setSize(width, height);
    }

    /**
     * Changes to new language
     * @param newLanguage language string
     */
    public void changeLanguageTo(String newLanguage){
        currentLanguage = newLanguage;
        updateTable();
    }

    /**
     * Updates the text strings in table
     */
    public void updateTable(){

        List<Map.Entry<String, String>> newDict = loadDict(getResourcePath());
        for (Object item: getColumns()){
            if (item instanceof TableColumn){
                TableColumn col = (TableColumn) item;
                col.setText(searchMapList(col.getId(), newDict));
            }
        }
    }

    /**
     * Adds one entry to the data list
     * @param key key string
     * @param value value string
     * @param isKey whether the key axis is the input area
     */
    public void addEntry(String key, String value, boolean isKey){
        if (isKey && !hasEntryKey(key) ) {
            data.add(new TableEntry(key, value));
        }
        else if (isKey && hasEntryKey(key)){
            data.removeIf(entry -> key.equals(entry.getKey()));
            data.add(new TableEntry(key, value));
        }

        if (!isKey && !hasEntryValue(value) ) {
            data.add(new TableEntry(key, value));
        }
        else if (!isKey && hasEntryValue(value)){
            data.removeIf(entry -> value.equals(entry.getValue()));
            data.add(new TableEntry(key, value));
        }

    }

    /**
     * Clear all entries in the data list
     */
    public void clearAll(){
        data.clear();
    }

    private void initializeTable(){
        setEditable(true);
        List<Map.Entry<String, String>> colDict = loadDict(getResourcePath());

        for (Map.Entry<String, String> col: colDict){
            initializeCol(col.getValue(), col.getKey(), col.getKey().equals(KEY_MATCH_TERM));
        }
        data = FXCollections.observableArrayList();
        setItems(data);

        getStylesheets().add(CSS_FILEPATH);
        setStyle("-fx-font-family: \"Consolas\";");
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

    private boolean hasEntryValue(String val){
        for (TableEntry entry:data){
            if (val.equals(entry.getValue())){
                return true;
            }
        }
        return false;
    }

    private void initializeCol(String colTitle, String colId, boolean editable){
        TableColumn col  = new TableColumn(colTitle);
        setColSize(col);
        col.setCellValueFactory(new PropertyValueFactory<TableEntry, String>(colId));
        if (editable) {
            col.setCellFactory(TextFieldTableCell.forTableColumn());
        }
        col.setId(colId);
        getColumns().add(col);
    }

    private void setColSize(TableColumn col){
        col.setPrefWidth(COLUMN_WIDTH);
    }

    private void setSize(double width, double height){
        this.width = width;
        this.height = height;
    }

    private List<Map.Entry<String, String>> loadDict(String resourcePath){
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }

    private String getResourcePath(){
        List<Map.Entry<String, String>> filepathDict = loadDict(LOCAL_RESOURCE_FILEPATH);

        for (Map.Entry<String, String> entry: filepathDict){
            if (entry.getKey().equals(type)){
                return String.format("%s%s%s", LOCAL_RESOURCE_PATH, currentLanguage, entry.getValue());
            }
        }

        System.out.println("Error: could not find the table type");
        return String.format("%s%s", LOCAL_RESOURCE_PATH, currentLanguage);
    }

}
