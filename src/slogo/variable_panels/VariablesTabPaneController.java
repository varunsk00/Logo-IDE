package slogo.variable_panels;

import slogo.compiler.Compiler;
import slogo.terminal.TerminalController;

import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class VariablesTabPaneController {
    private final static String COMMAND_TYPE = "COMMAND";
    private final static String DEFINED_TYPE = "DEFINED";
    private final static String VAR_TYPE = "VAR";

    private final static String DEFINED_VALUE_PLACEHOLDER = "";

    private VariablesTabPaneView variablesTabPaneView;
    private Compiler compiler;
    private TerminalController terminal;

    public VariablesTabPaneController(VariablesTabPaneView view, Compiler c, TerminalController t){
        this.variablesTabPaneView = view;
        this.compiler = c;
        this.terminal = t;
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
            variablesTabPaneView.addEntry(VAR_TYPE, var, Double.toString(compiler.getVariable(var)));
        }
    }

    private void updateDefinedMethod(){
        Set<String> methodList = (Set<String>) compiler.getAllUserDefinedCommands();
        for (String method:methodList){
            variablesTabPaneView.addEntry(DEFINED_TYPE, method, DEFINED_VALUE_PLACEHOLDER);
        }
    }

    private void updateCommand(){

        List<String> commandList = terminal.getAllCommands();
        Iterator<String> commandIterator = commandList.iterator();

        List<String> messageList = terminal.getAllMessages();
        Iterator<String> messageIterator = messageList.iterator();


        while (commandIterator.hasNext() && messageIterator.hasNext()) {
            variablesTabPaneView.addEntry(COMMAND_TYPE, commandIterator.next(), messageIterator.next());
        }
    }



}
