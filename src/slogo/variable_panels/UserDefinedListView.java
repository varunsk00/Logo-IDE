package slogo.variable_panels;


import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

public class UserDefinedListView extends ListView<String> {
    public UserDefinedListView(int width, int height){
        super(FXCollections.observableArrayList());
    }
}
