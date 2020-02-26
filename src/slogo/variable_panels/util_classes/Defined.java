package slogo.variable_panels.util_classes;

import javafx.beans.property.SimpleStringProperty;

public class Defined extends TableEntry {
    private final SimpleStringProperty methodName;
    private final SimpleStringProperty methodContent;

    public Defined(String name, String content) {
        this.methodName = new SimpleStringProperty(name);
        this.methodContent = new SimpleStringProperty(content);
    }

    public String getMethodName(){
        return methodName.get();
    }

    public void setMethodName(String method){
        methodName.set(method);
    }

    public String getMethodContent(){
        return methodContent.get();
    }

    public void setMethodContent(String content){
        methodContent.set(content);
    }
}
