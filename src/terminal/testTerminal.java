package terminal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import terminal.utils.UI.InputPanel;

public class testTerminal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test");

        InputPanel panel = new InputPanel(50, 3);
        Scene sceneVis = new Scene(panel);

        stage.setScene(sceneVis);
        stage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }

}
