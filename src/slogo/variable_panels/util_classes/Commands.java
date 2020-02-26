package slogo.variable_panels.util_classes;

import javafx.beans.property.SimpleStringProperty;

public class Commands extends TableEntry {
    private final SimpleStringProperty commandLine;
    private final SimpleStringProperty commandMessage;

    public Commands(String line, String message) {
        this.commandLine = new SimpleStringProperty(line);
        this.commandMessage = new SimpleStringProperty(message);
    }

    public String getCommandLine(){
        return commandLine.get();
    }

    public void setCommandLine(String line){
        commandLine.set(line);
    }

    public String getCommandMessage(){
        return commandMessage.get();
    }

    public void setCommandMessage(String message){
        commandMessage.set(message);
    }
}
