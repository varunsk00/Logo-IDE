package terminal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import terminal.utils.UI.InputPanel;
import terminal.utils.UI.OutputPanel;

public class testTerminal extends Application {

    @Override
    public void start(Stage stage) throws Exception {
        stage.setTitle("Test");

        //InputPanel panel = new InputPanel(20, 25);

        TerminalView terminalView = new TerminalView(1000, 800);
        TerminalController terminalController = new TerminalController(terminalView);

        Scene sceneVis = new Scene(terminalView);

        stage.setScene(sceneVis);
        stage.show();

    }

    public static void main (String[] args) {
        launch(args);
    }

}
