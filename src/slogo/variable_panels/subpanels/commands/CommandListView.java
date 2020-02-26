package slogo.variable_panels.subpanels.commands;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;

import javax.swing.text.Element;


public class CommandListView extends ListView<String> {
    public CommandListView(int width, int max_height) {
        super(FXCollections.observableArrayList());
    }
}
