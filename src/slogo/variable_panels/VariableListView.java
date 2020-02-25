package slogo.variable_panels;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

public class VariableListView extends ListView<String> {
    public VariableListView(int width, int height) {
        super(FXCollections.observableArrayList());
    }
}
