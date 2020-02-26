package slogo.variable_panels.subpanels.variables;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

public class VariableListView extends ListView<String> {
    public VariableListView(double width, double height) {
        super(FXCollections.observableArrayList());
    }
}
