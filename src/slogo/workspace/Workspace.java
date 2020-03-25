package slogo.workspace;

import java.io.FileNotFoundException;
import javafx.scene.layout.BorderPane;
import javafx.scene.paint.Color;
import slogo.compiler.parser.Compiler;
import slogo.terminal.TerminalController;
import slogo.terminal.TerminalView;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleHabitat;
import slogo.turtle.TurtleView;

/**
 * Workspace.java
 * Organizes Turtle Habitat, Terminal, and Variable Explorer into a Borderpane
 *
 * @author Varun Kosgi
 * @author Qiaoyi Fang
 */
public class Workspace extends BorderPane {

  private static final double HEADER_HEIGHT = 110;
  private static final double TAB_HEIGHT = 30;
  private int status;
  private double variableExplorerHeight;
  private double terminalWidth;
  private double terminalHeight;
  private double habitatWidth;
  private double habitatHeight;
  private double deltaX;
  private double deltaY;
  private double sumX;
  private double sumY;
  private TurtleHabitat myHabitat;
  private TerminalView myTerminalView;
  private TerminalController myTerminalController;
  private ColorFactory myColorFactory;
  private String DEFAULT_PREF = "MULTI_TURTLES"; // "TURTLE_SHAPE"
  private Color defaultBackgroundColor = Color.WHITE;
  private Color defaultPenColor = Color.BLACK;
  private Compiler comp;
  private PrefProcessor prefProcessor;

  /**
   * Constructor to intialize Workspace- Terminal to left, Variable Explorer to bottom, Turtle Habitat to right
   * @param width width of workspace
   * @param height of workspace
   * @throws FileNotFoundException in case File cannot be found
   */
  public Workspace(double width, double height) throws FileNotFoundException {
    myColorFactory = new ColorFactory();
    prefProcessor = new PrefProcessor();
    setSizes(width, height);
    startCompiler();
    setPrefWidth(width);
    setPrefHeight(height);
    setTurtleHabitat();
    setTerminalView();
    prefProcessor.initializeWorkspace(this, DEFAULT_PREF);
  }

  /**
   * @return this Workspace's instance of the PreferenceProcessor
   */
  public PrefProcessor getPrefProcessor() {
    return prefProcessor;
  }

  /**
   * @return status of Terminal Controller
   */
  public int getStatus() {
    return status;
  }

  /**
   * Sets status of Terminal Controller
   * @param stat
   */
  public void setStatus(int stat) {
    this.status = stat;
  }

  /**
   * @return this Workspace's Compiler
   */
  public Compiler getCompiler() {
    return comp;
  }

  /**
   * @return default background color
   */
  public Color getDefaultBackgroundColor() {
    return defaultBackgroundColor;
  }

  /**
   * @param newColor to serves as default background color
   */
  public void setDefaultBackgroundColor(Color newColor) {
    defaultBackgroundColor = newColor;
  }

  /**
   * @param newColor to serves as default pen color
   */
  public void setDefaultPenColorColor(Color newColor) {
    defaultPenColor = newColor;
  }

  /**
   * @return default pen color
   */
  public Color getDefaultPenColor() {
    return defaultPenColor;
  }

  /**
   * Set the Pen color of a specific Turtle
   * @param turtleID selected turtle
   * @param c new color
   */
  public void setTurtlePenColor(int turtleID, Color c) {
    int idx = myColorFactory.addColor(c);
    TurtleView tview = getHabitat().getTurtleView(turtleID);
    Turtle t = getCompiler().getTurtleByID(turtleID);
    tview.setPenColor(c);
    t.setPenColorIndex(idx);
  }

  /**
   * @return this Workspace's TerminalController
   */
  public TerminalController getTerminalController() {
    return myTerminalController;
  }

  /**
   * @return this Workspace's TurtleHabitat
   */
  public TurtleHabitat getHabitat() {
    return myHabitat;
  }

  /**
   * @return this Workspace's Terminal
   */
  public TerminalView getTerminal() {
    return myTerminalView;
  }

  /**
   * @param str string that is title of Tab
   * @return last character converted to int
   */
  public int getCurrentWorkspace(String str) {
    int i = str.length();
    while (i > 0 && Character.isDigit(str.charAt(i - 1))) {
      i--; }
    return Integer.parseInt(str.substring(i));
  }

  private void startCompiler() {
    comp = new Compiler();
  }

  private void setSizes(double scene_width, double scene_height) {
    habitatWidth = scene_width / 2;
    variableExplorerHeight = scene_height / 5;
    habitatHeight = scene_height - HEADER_HEIGHT - variableExplorerHeight;
    terminalWidth = scene_width / 2;
    terminalHeight = scene_height - HEADER_HEIGHT - variableExplorerHeight - TAB_HEIGHT;
  }

  private void setTerminalView() {
    myTerminalView = new TerminalView((int) terminalWidth, (int) terminalHeight);
    myTerminalController = new TerminalController(myTerminalView);
    myTerminalController.setCompiler(comp);
    myTerminalController.setHabitat(myHabitat);
    myTerminalController.setWorkspace(this);
    status = -1;
    setLeft(myTerminalView);
  }

  private void setTurtleHabitat() { //alows for mouse dragging of habitat
    myHabitat = new TurtleHabitat(habitatWidth, habitatHeight);
    deltaX = 0;
    deltaY = 0;
    myHabitat.setOnMouseDragged(event -> {
      if (deltaX != 0 && deltaY != 0) {
        sumX += event.getSceneX() - deltaX;
        sumY += event.getSceneY() - deltaY;
        myHabitat.setTranslateX(sumX);
        deltaX = event.getSceneX();
        myHabitat.setTranslateY(sumY);
        deltaY = event.getSceneY(); }
      else {
        sumX = 0;
        sumY = 0;
        deltaX = event.getSceneX();
        deltaY = event.getSceneY(); } });
    myHabitat.setOnMouseReleased(event -> {
      deltaX = 0;
      deltaY = 0; });
    setRight(myHabitat);
  }

  public void setColorFactory(ColorFactory cf) {
    myColorFactory = cf;
  }
}
