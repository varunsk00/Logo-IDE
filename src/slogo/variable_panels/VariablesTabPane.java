package slogo.variable_panels;

import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

public class VariablesTabPane extends TabPane {
    private final static String[] tabNames = {"Command history", "Defined Methods", "Variables"};

    public VariablesTabPane(double width, double height){
        setPrefSize(width, height);
    }

    private void initializeTabPane(){
        for (String tabName: tabNames){
        }
    }
}
