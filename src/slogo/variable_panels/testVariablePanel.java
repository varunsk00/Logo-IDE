package slogo.variable_panels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testVariablePanel extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test");

        VariablesTabPaneView view = new VariablesTabPaneView(1000, 400);
        VariablesTabPaneController controller = new VariablesTabPaneController(view);

        Scene sceneVis = new Scene(view);

        stage.setScene(sceneVis);
        stage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }
}
