package slogo.variable_panels.subpanels;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import slogo.compiler.Command;
import slogo.variable_panels.util_classes.Commands;
import slogo.variable_panels.util_classes.Defined;
import slogo.variable_panels.util_classes.Variable;

import java.util.*;

public class AutoTableView extends TableView {
    private final static String LOCAL_RESOURCE_PATH = "slogo.variable_panels.local_resources.";

    private final static String COMMAND_PATH = "command_col_titles";
    private final static String DEFINED_PATH = "defined_col_titles";
    private final static String VAR_PATH = "var_col_titles";

    private final static String COMMAND_TYPE = "COMMAND";
    private final static String DEFINED_TYPE = "DEFINED";
    private final static String VAR_TYPE = "VAR";

    private final static int colNum = 2;

    private double width;
    private double height;

    private String currentResourcePath;
    private ObservableList data;
    private String type;

    public AutoTableView(double width, double height, String type) {
        super();
        this.type = type;
        factoryType();
        initializeTable();
        setSize(width, height);
    }

    private void initializeTable(){
        setEditable(false);
        List<Map.Entry<String, String>> colDict = loadDict();
        for (Map.Entry<String, String> col: colDict){
            initializeCol(col.getValue(), col.getKey());
        }
        data = FXCollections.observableArrayList();
        setItems(data);
    }

    public void addEntry(slogo.variable_panels.util_classes.TableEntry entry){
        if (entry instanceof Variable && type.equals(VAR_TYPE) || entry instanceof Defined && type.equals(DEFINED_TYPE) || entry instanceof Commands && type.equals(COMMAND_TYPE))
            data.add(entry);
        else
            System.out.println("DataType unimplemented in panel");
    }

    private void initializeCol(String colTitle, String cellFactory){
        TableColumn col  = new TableColumn(colTitle);
        setColSize(col, getColWidth());
        if (type.equals(COMMAND_TYPE)){
            col.setCellValueFactory(new PropertyValueFactory<Commands, String>(cellFactory));
        }
        else if (type.equals(VAR_TYPE)){
            col.setCellValueFactory(new PropertyValueFactory<Variable, String>(cellFactory));
        }
        else if (type.equals(DEFINED_TYPE)){
            col.setCellValueFactory(new PropertyValueFactory<Defined, String>(cellFactory));
        }
        else {
            System.out.println("unimplemented type in variable panel, default to commands");
            col.setCellValueFactory(new PropertyValueFactory<Commands, String>(cellFactory));
        }
        getColumns().add(col);
    }

    private void setColSize(TableColumn col, double width){
        col.setMinWidth(width);
    }

    private double getColWidth(){
        return width/colNum;
    }

    private void setSize(double width, double height){
        this.width = width;
        this.height = height;
    }

    private List<Map.Entry<String, String>> loadDict(){
        ResourceBundle resources = ResourceBundle.getBundle(currentResourcePath);
        List<Map.Entry<String, String>> dict = new ArrayList<>();
        for (String tabType: Collections.list(resources.getKeys())){
            String tabName = resources.getString(tabType);
            dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
        }
        return dict;
    }

    private void factoryType(){
        System.out.println(type);
        System.out.println(COMMAND_TYPE);
        switch (type){
            case COMMAND_TYPE:
                currentResourcePath = String.format("%s%s", LOCAL_RESOURCE_PATH, COMMAND_PATH);
                break;
            case VAR_TYPE:
                currentResourcePath = String.format("%s%s", LOCAL_RESOURCE_PATH, VAR_PATH);
                break;
            case DEFINED_TYPE:
                currentResourcePath = String.format("%s%s", LOCAL_RESOURCE_PATH, DEFINED_PATH);
                break;
        }
    }

}
