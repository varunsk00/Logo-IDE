package slogo.variable_panels.util_classes;

import javafx.beans.property.SimpleStringProperty;

public class TableEntry {
    private final SimpleStringProperty key;
    private final SimpleStringProperty value;

    public TableEntry(String key, String value) {
        this.key = new SimpleStringProperty(key);
        this.value = new SimpleStringProperty(value);
    }

    public String getKey(){
        return key.get();
    }

    public void setKey(String str){
        key.set(str);
    }

    public String getValue(){
        return value.get();
    }

    public void setValue(String str){
        value.set(str);
    }
}
