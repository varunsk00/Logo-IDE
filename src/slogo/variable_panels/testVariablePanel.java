package slogo.variable_panels;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testVariablePanel extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test");




        Scene sceneVis = new Scene();

        stage.setScene(sceneVis);
        stage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }
}
