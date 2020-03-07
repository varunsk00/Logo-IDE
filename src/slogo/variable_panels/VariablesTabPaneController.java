package slogo.variable_panels;

import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.event.EventHandler;
import javafx.scene.control.TableColumn;
import slogo.compiler.parser.Compiler;
import slogo.terminal.TerminalController;
import slogo.variable_panels.subpanels.AutoTableView;
import slogo.variable_panels.util_classes.TableEntry;

/**
 * the Controller object that manages the tap pane (includes variable explore, history commands, and
 * user-defined methods)
 */
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

  /**
   * Constructor
   *
   * @param view the view object
   * @param c    the compiler
   * @param t    the terminal controller
   */
  public VariablesTabPaneController(VariablesTabPaneView view, Compiler c, TerminalController t) {
    this.variablesTabPaneView = view;
    this.compiler = c;
    this.terminal = t;
    tablesEventBinding(view.getTableDict());
  }

  /**
   * Updates all tables in the TabPane
   */
  public void updateAllTables() {
    updateVariableTable();
    updateDefinedMethod();
    updateCommand();
  }

  /**
   * Changes language
   *
   * @param language language ///////////////////
   */
  public void changeLanguage(String language) {
    variablesTabPaneView.changeLanguageTo(language);
  }

  /**
   * Updates compiler
   *
   * @param newCompiler the new compiler object
   */
  public void updateCompiler(Compiler newCompiler) {
    this.compiler = newCompiler;
  }

  /**
   * Updates terminal
   *
   * @param newTerminal the new terminal object
   */
  public void updateTerminal(TerminalController newTerminal) {
    this.terminal = newTerminal;
  }

  private void updateVariableTable() {
    Set<String> varList = (Set<String>) compiler.getAllVariableNames();
    variablesTabPaneView.clearAll(VAR_TYPE);
    for (String var : varList) {
      variablesTabPaneView
          .addEntry(VAR_TYPE, Double.toString(compiler.getVariable(var)), formatVariable(var),
              false);
    }
  }

  private void updateDefinedMethod() {
    Set<String> methodList = (Set<String>) compiler.getAllUserDefinedCommands();
    variablesTabPaneView.clearAll(DEFINED_TYPE);
    for (String method : methodList) {
      variablesTabPaneView.addEntry(DEFINED_TYPE, DEFINED_VALUE_PLACEHOLDER, method, false);
    }
  }

  private void updateCommand() {

    List<String> commandList = terminal.getAllCommands();
    Iterator<String> commandIterator = commandList.iterator();

    List<String> messageList = terminal.getAllMessages();
    Iterator<String> messageIterator = messageList.iterator();

    variablesTabPaneView.clearAll(COMMAND_TYPE);
    while (commandIterator.hasNext() && messageIterator.hasNext()) {
      variablesTabPaneView
          .addEntry(COMMAND_TYPE, commandIterator.next(), messageIterator.next(), true);
    }
  }

  private void tablesEventBinding(List<Map.Entry<String, AutoTableView>> tableDict) {
    for (Map.Entry<String, AutoTableView> entry : tableDict) {

      for (Object item : entry.getValue().getColumns()) {
        TableColumn col = (TableColumn) item;
                /*if (item instanceof TableColumn){
                    col = (TableColumn) item;}
                else{
                    System.out.println("Error: unimplemented type of table column");
                    break;
                }*/

        if (col.getId().equals(EDITABLE_MATCH_KEYWORD) && entry.getKey().equals(VAR_TYPE)) {
          varTableBinding(col);
        } else if (col.getId().equals(EDITABLE_MATCH_KEYWORD) && entry.getKey()
            .equals(DEFINED_TYPE)) {
          definedTableBinding(col);
        } else if (col.getId().equals(EDITABLE_MATCH_KEYWORD) && entry.getKey()
            .equals(COMMAND_TYPE)) {
          commandTableBinding(col);
        } else if (col.getId().equals(EDITABLE_MATCH_KEYWORD)) {
          System.out.println("Error: unimplemented variable explore panel");
        }
      }
    }
  }

  private void varTableBinding(TableColumn col) {

    col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<TableEntry, String>>)
        cellEditEvent -> {
          int cellPos = cellEditEvent.getTablePosition().getRow();
          TableEntry edited = cellEditEvent.getTableView().getItems().get(cellPos);
          terminal.sendInput(generateSetCommand(edited.getValue(), cellEditEvent.getNewValue()));
          (cellEditEvent.getTableView().getItems().get(cellPos))
              .setKey(cellEditEvent.getOldValue());
        });
  }

  private void definedTableBinding(TableColumn col) {
    col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<TableEntry, String>>)
        cellEditEvent -> {
          int cellPos = cellEditEvent.getTablePosition().getRow();
          TableEntry edited = cellEditEvent.getTableView().getItems().get(cellPos);
          terminal
              .sendInput(generateDefinedCommand(edited.getValue(), cellEditEvent.getNewValue()));
          (cellEditEvent.getTableView().getItems().get(cellPos)).setKey(DEFINED_VALUE_PLACEHOLDER);
        });
  }

  private void commandTableBinding(TableColumn col) {
    col.setOnEditCommit((EventHandler<TableColumn.CellEditEvent<TableEntry, String>>)
        cellEditEvent -> {
          int cellPos = cellEditEvent.getTablePosition().getRow();
          terminal.sendInput(generateHistoryCommand(cellEditEvent.getNewValue()));
          (cellEditEvent.getTableView().getItems().get(cellPos))
              .setKey(cellEditEvent.getOldValue());
        });
  }

  private String generateSetCommand(String var, String val) {
    return String.format("set :%s %s", var, val);
  }

  private String generateDefinedCommand(String method, String vals) {
    return String.format("%s %s", method, vals);
  }

  private String generateHistoryCommand(String c) {
    Matcher matcher = Pattern.compile(COMMAND_DISPLAY_PATTERN).matcher(c);
    if (matcher.matches()) {
      return matcher.group(1);
    } else {
      return c;
    }
  }

  private String formatVariable(String var) {
    return var.substring(1);
  }
}
