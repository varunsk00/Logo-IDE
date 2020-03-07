package slogo.workspace;

import javafx.scene.layout.BorderPane;
import slogo.compiler.parser.Compiler;
import slogo.terminal.TerminalController;
import slogo.terminal.TerminalView;
import slogo.turtle.TurtleHabitat;

public class Workspace extends BorderPane {

  private static final double HEADER_HEIGHT = 80;
  private static final double TAB_HEIGHT = 30;
  private TurtleHabitat myHabitat;
  private TerminalView myTerminalView;
  private TerminalController myTerminalController;
  private ColorFactory myColorFactory;
  private int status;
  private double VARIABLE_EXPLORER_HEIGHT;
  private double TERMINAL_WIDTH;
  private double TERMINAL_HEIGHT;
  private double HABITAT_WIDTH;
  private double HABITAT_HEIGHT;

  private double deltaX;
  private double deltaY;
  private double sumX;
  private double sumY;
  private Compiler comp;

  public Workspace(double width, double height) {
    myColorFactory = new ColorFactory();
    setSizes(width, height);
    startCompiler();
    setPrefWidth(width);
    setPrefHeight(height);
    setTurtleHabitat();
    setTerminalView();
  }

  public int getStatus() {
    return status;
  }

  public void setStatus(int stat) {
    this.status = stat;
  }

  public Compiler getCompiler() {
    return comp;
  }

  public TerminalController getTerminalController() {
    return myTerminalController;
  }

  public TurtleHabitat getHabitat() {
    return myHabitat;
  }

  public TerminalView getTerminal() {
    return myTerminalView;
  }

  public int getCurrentWorkspace(String str) {
    int i = str.length();
    while (i > 0 && Character.isDigit(str.charAt(i - 1))) {
      i--;
    }
    return Integer.parseInt(str.substring(i));
  }

  private void startCompiler() {
    comp = new Compiler();
  }

  private void setSizes(double scene_width, double scene_height) {
    HABITAT_WIDTH = scene_width / 2;
    VARIABLE_EXPLORER_HEIGHT = scene_height / 5;
    HABITAT_HEIGHT = scene_height - HEADER_HEIGHT - VARIABLE_EXPLORER_HEIGHT;
    TERMINAL_WIDTH = scene_width / 2;
    TERMINAL_HEIGHT = scene_height - HEADER_HEIGHT - VARIABLE_EXPLORER_HEIGHT - TAB_HEIGHT;
  }

  private void setTerminalView() {
    myTerminalView = new TerminalView((int) TERMINAL_WIDTH, (int) TERMINAL_HEIGHT);
    myTerminalController = new TerminalController(myTerminalView);
    myTerminalController.setCompiler(comp);
    myTerminalController.setHabitat(myHabitat);
    status = -1;
    setLeft(myTerminalView);
  }

  private void setTurtleHabitat() {
    myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT);
    deltaX = 0;
    deltaY = 0;

    myHabitat.setOnMouseDragged(event -> {

      if (deltaX != 0 && deltaY != 0) {
        sumX += event.getSceneX() - deltaX;
        sumY += event.getSceneY() - deltaY;
        myHabitat.setTranslateX(sumX);
        deltaX = event.getSceneX();
        myHabitat.setTranslateY(sumY);
        deltaY = event.getSceneY();
      } else {
        sumX = 0;
        sumY = 0;
        deltaX = event.getSceneX();
        deltaY = event.getSceneY();
      }

    });

    myHabitat.setOnMouseReleased(event -> {
      deltaX = 0;
      deltaY = 0;
    });

    setRight(myHabitat);
  }
}
