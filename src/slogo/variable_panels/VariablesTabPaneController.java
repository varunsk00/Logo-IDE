package slogo.variable_panels;

import javafx.event.EventHandler;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import slogo.compiler.Compiler;
import slogo.terminal.TerminalController;
import slogo.variable_panels.subpanels.AutoTableView;
import slogo.variable_panels.util_classes.TableEntry;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class VariablesTabPaneController {
    private final static String COMMAND_TYPE = "COMMAND";
    private final static String DEFINED_TYPE = "DEFINED";
    private final static String VAR_TYPE = "VAR";

    private final static String DEFINED_VALUE_PLACEHOLDER = "";

    private final static String COMMAND_DISPLAY_PATTERN = "[0-9]+:\\s?(.*)";
    private final static String EDITABLE_MATCH_KEYWORD = "key";

    private VariablesTabPaneView variablesTabPaneView;
    private Compiler compiler;
    private TerminalController terminal;

    public VariablesTabPaneController(VariablesTabPaneView view, Compiler c, TerminalController t){
        this.variablesTabPaneView = view;
        this.compiler = c;
        this.terminal = t;
        tablesEventBinding(view.getTableDict());
    }

    public void updateAllTables(){
        updateVariableTable();
        updateDefinedMethod();
        updateCommand();
    }

    public void changeLanguage(String language){
        variablesTabPaneView.changeLanguageTo(language);
    }

    private void updateVariableTable(){
        Set<String> varList = (Set<String>) compiler.getAllVariableNames();
        for (String var:varList){
            variablesTabPaneView.addEntry(VAR_TYPE, Double.toString(compiler.getVariable(var)), formatVariable(var), false);
        }
    }

    private void updateDefinedMethod(){
        Set<String> methodList = (Set<String>) compiler.getAllUserDefinedCommands();
        for (String method:methodList){
            variablesTabPaneView.addEntry(DEFINED_TYPE, DEFINED_VALUE_PLACEHOLDER, method, false);
        }
    }

    private void updateCommand(){

        List<String> commandList = terminal.getAllCommands();
        Iterator<String> commandIterator = commandList.iterator();

        List<String> messageList = terminal.getAllMessages();
        Iterator<String> messageIterator = messageList.iterator();


        while (commandIterator.hasNext() && messageIterator.hasNext()) {
            variablesTabPaneView.addEntry(COMMAND_TYPE, commandIterator.next(), messageIterator.next(), true);
        }
    }

    /*
    var - variable :change compiler value (auto generate command and send to terminal)
    defined - auto generate command (put into numbers)
    history commands - directly execute
    */

    private void tablesEventBinding(List<Map.Entry<String, AutoTableView>> tableDict) {
        for (Map.Entry<String, AutoTableView> entry : tableDict) {
            if (entry.getKey().equals(VAR_TYPE)){
                varTableBinding(entry.getValue());
            }
            else if (entry.getKey().equals(DEFINED_TYPE)){
                definedTableBinding(entry.getValue());
            }
            else if (entry.getKey().equals(COMMAND_TYPE)){
                commandTableBinding(entry.getValue());
            }
            else {
                System.out.println("Error: unimplemented variable explore panel");
            }
        }
    }

    // var
    private void varTableBinding(AutoTableView tableDict){
        for (Object item:tableDict.getColumns()){
            if (item instanceof TableColumn){
                TableColumn col = (TableColumn) item;
                if (col.getId().equals(EDITABLE_MATCH_KEYWORD)){
                    col.setOnEditCommit(
                            (EventHandler<TableColumn.CellEditEvent<TableEntry, String>>) cellEditEvent -> {
                                int cellPos = cellEditEvent.getTablePosition().getRow();
                                TableEntry edited = cellEditEvent.getTableView().getItems().get(cellPos);
                                terminal.sendInput(generateSetCommand(edited.getValue(), cellEditEvent.getNewValue()));//not sure if this works
                                (cellEditEvent.getTableView().getItems().get(cellPos)).setKey(cellEditEvent.getOldValue());
                            });
                }
            }
            else{
                System.out.println("Error: unimplemented type of table column");
            }
        }
    }

    // defined
    private void definedTableBinding(AutoTableView tableDict){}

    //history
    private void commandTableBinding(AutoTableView tableDict){}

    private String generateSetCommand(String var, String val){
        return String.format("set :%s %s", var, val);
    }

    private String generateDefinedCommand(String method, String vals){
        return String.format("%s %s", method, vals);
    }

    private String generateHistoryCommand(String c){
        Matcher matcher = Pattern.compile(COMMAND_DISPLAY_PATTERN).matcher(c);
        if (matcher.matches()) return matcher.group(1);
        else return c;
    }

    private String formatVariable(String var){
        return var.substring(1);
    }
}
