package slogo.terminal;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class testTerminal extends Application {

  public static void main(String[] args) {
    launch(args);
  }

  @Override
  public void start(Stage stage) throws Exception {
    stage.setTitle("Test");

    //InputPanel panel = new InputPanel(20, 25);

    TerminalView terminalView = new TerminalView(400, 800);
    TerminalController terminalController = new TerminalController(terminalView);

    Scene sceneVis = new Scene(terminalView);

    stage.setScene(sceneVis);
    stage.show();

  }

}
