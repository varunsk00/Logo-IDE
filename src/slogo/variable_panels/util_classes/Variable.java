package slogo.variable_panels.util_classes;

import javafx.beans.property.SimpleStringProperty;

public class Variable extends TableEntry {
    private final SimpleStringProperty variableName;
    private final SimpleStringProperty variableValue;

    public Variable(String name, String value) {
        this.variableName = new SimpleStringProperty(name);
        this.variableValue = new SimpleStringProperty(value);
    }

    public String getVariableName(){
        return variableName.get();
    }

    public void setVariableName(String name){
        variableName.set(name);
    }

    public String getVariableValue(){
        return variableValue.get();
    }

    public void setVariableValue(String content){
        variableValue.set(content);
    }
}