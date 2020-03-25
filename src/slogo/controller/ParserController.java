package slogo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleView;
import slogo.variable_panels.VariablesTabPaneController;
import slogo.workspace.ColorFactory;
import slogo.workspace.Workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.*;

/**
 * ParserController.java Sets up and runs Slogo environment as an Application
 *
 * @author Varun Kosgi
 * @author Qiaoyi Fang
 * @author Alexander Uzochukwu
 */
public class ParserController extends Application {

  private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
  private static final String IMAGE_DIRECTORY = "src/slogo/resources/images";
  private static final String LOGO_DIRECTORY = "data/examples";
  private static final String RESOURCES_PACKAGE = "slogo.resources.languages.GUI.";
  private static final String IMAGE_FILE_EXTENSIONS = "*.png,*.jpg";
  private static final String LOGO_FILE_EXTENSIONS = "*.logo";
  private static final double FRAMES_PER_SECOND = 30;
  private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;
  private static final double SCENE_WIDTH = 1280;
  private static final double SCENE_HEIGHT = 720;
  private static final double TABPANE_WIDTH = SCENE_WIDTH;
  private static final double TABPANE_HEIGHT = SCENE_HEIGHT / 5;
  private static final Color ALL_COLOR = Color.WHITE;
  private static final int NUMBER_OF_TABS = 10;
  private static String guiLanguage = "English_GUI";
  private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + guiLanguage);
  private static int currentTab;
  private static String currentLang = "English";

  private static FileSelect imageFile = new FileSelect(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY,
          myResources.getString("ImageFile"), RESOURCES_PACKAGE + guiLanguage);
  private static FileSelect logoFile = new FileSelect(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY,
          myResources.getString("Logo"), RESOURCES_PACKAGE + guiLanguage);

  private BorderPane root;
  private Header header;
  private Footer footer;

  private Stage myStage;
  private Timeline animation;
  private int DEFAULT_COLOR_CODE = -1;

  private Workspace currentWorkspace;
  private List<Workspace> workspaces;

  private TabPane workspaceEnvironment;

  private VariablesTabPaneController tabPaneController;

  private ColorFactory cf = new ColorFactory();
  private List<Button> selectButtons = new ArrayList<>();

  /**
   * Empty Constructor needed to run the application due to Application requirements
   */
  public ParserController() {
  }

  /**
   * Constructor used in Main to begin the program Begins our JavaFX application Starts the
   * Animation Loop and sets the Border Pane, filling it with a ButtonController, SliderController,
   * and TurtleHabitat, TerminalView, and VariablesTabPaneView Sets the stage and scene and shows
   * it
   *
   * @param args is the String[] passed in by main
   */
  public ParserController(String[] args) {
    launch(args);
  }

  /**
   * @param primaryStage is the stage to display the Application, runs loop, organizes elements
   *                     BorderPane
   */
  public void start(Stage primaryStage) throws FileNotFoundException {
    primaryStage.setTitle("SLogo");
    myStage = primaryStage;
    startWorkspaces();
    startAnimationLoop();
    setBorderPane();
    setHeader();
    setFooter();
    Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
    scene.getStylesheets().add(STYLESHEET);
    myStage.setScene(scene);
    myStage.show();
  }


  private void startWorkspaces() throws FileNotFoundException {
    workspaces = new ArrayList<>();
    workspaces.add(null);
    for (int i = 0; i < NUMBER_OF_TABS; i++) {
      workspaces.add(new Workspace((SCENE_WIDTH), SCENE_HEIGHT));
      workspaces.get(workspaces.size()-1).setColorFactory(cf); }
    currentWorkspace = workspaces.get(1);
    currentTab = 1;
    workspaceEnvironment = new TabPane();
    for (int i = 1; i < workspaces.size(); i++) {
      Tab tab = new Tab(myResources.getString("Workspace") + i);
      tab.setContent(workspaces.get(i));
      workspaceEnvironment.getTabs().add(tab); }
  }

  private void setBorderPane() {
    root = new BorderPane();
    root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
    root.setMaxWidth(SCENE_WIDTH);
    root.setMaxHeight(SCENE_HEIGHT);
    root.setCenter(workspaceEnvironment);
  }

  private void setHeader() throws FileNotFoundException {
    header = new Header(RESOURCES_PACKAGE + guiLanguage);
    root.setTop(header);
  }

  private void setFooter() throws FileNotFoundException {
    footer = new Footer(TABPANE_WIDTH, TABPANE_HEIGHT, RESOURCES_PACKAGE + guiLanguage);
    tabPaneController = new VariablesTabPaneController(footer.getVariableExplorer(), currentWorkspace.getCompiler(),
      currentWorkspace.getTerminalController());
    root.setBottom(footer);
  }

  private void startAnimationLoop() {
    KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
      try {
        step(); }
      catch (IOException ex) {
        System.out.println("Help Text File Not Found."); } });
    animation = new Timeline();
    animation.setCycleCount(Timeline.INDEFINITE);
    animation.getKeyFrames().add(frame);
    animation.play();
  }

  private void step() throws IOException {
    currentWorkspace.getTerminalController()
            .setSize((int) myStage.getWidth() / 2, (int) (myStage.getHeight() - 2 * TABPANE_HEIGHT));
    updateHabitatBackgroundColor();
    currentWorkspace.getCompiler().setLanguage(currentLang);
    header.getSliders().updateZoom(currentWorkspace);
    updateCurrentWorkspace();
    updateTabPanes(false);
    handleMultipleTurtles();
    handleLanguage(header.getButtons().getLanguageStatus());
    handleButtonActions();
  }

  private void handleButtonActions() throws IOException {
    if (header.getButtons().getFileStatus()) {
      handleLogoFiles(); }
    if (header.getButtons().getImageStatus()) {
      handleImageFileChooser(); }
    if (!header.getButtons().getHelpStatus().equals(myResources.getString("HelpButton"))) {
      header.launchHelpWindow(myResources.getString(header.getButtons().getHelpStatus()), guiLanguage); }
    if (header.getButtons().getPenColorStatus()) {
      header.launchPenColorChooser(currentWorkspace, RESOURCES_PACKAGE + guiLanguage, selectButtons); }
    if (header.getButtons().getBackgroundColorStatus()) {
      header.launchBackgroundColorChooser(currentWorkspace, RESOURCES_PACKAGE + guiLanguage); }
    if (header.getButtons().isViewAllTurtles()) {
      currentWorkspace.getHabitat().viewTurtleInformation();
      header.getButtons().setViewAllTurtlesOff(); }
    if (footer.getButtons().getUpPressed()){
      footer.getButtons().executeUp(currentWorkspace); }
    if (footer.getButtons().getDownPressed()){
      footer.getButtons().executeDown(currentWorkspace); }
    if (footer.getButtons().getLeftPressed()){
      footer.getButtons().executeLeft(currentWorkspace); }
    if (footer.getButtons().getRightPressed()){
      footer.getButtons().executeRight(currentWorkspace); }
  }

  private void updateTabPanes(boolean isSwitch) {
    if (currentWorkspace.getStatus() != currentWorkspace.getTerminalController().getStatus() || isSwitch) {
      currentWorkspace.setStatus(currentWorkspace.getTerminalController().getStatus());
      tabPaneController.updateAllTables(); }
  }

  private void updateCurrentWorkspace() {
    String workspaceString = workspaceEnvironment.getSelectionModel().getSelectedItem().getText();
    int current = currentWorkspace.getCurrentWorkspace(workspaceString);
    currentWorkspace = workspaces.get(current);
    updateColorFactory();
    if (current != currentTab) {
      currentTab = current;
      tabPaneController.updateCompiler(currentWorkspace.getCompiler());
      tabPaneController.updateTerminal(currentWorkspace.getTerminalController());
      updateTabPanes(true); }
  }

  private void updateHabitatBackgroundColor() {
    int compilerColorID = currentWorkspace.getCompiler().getBackgroundColor();
    if (compilerColorID != DEFAULT_COLOR_CODE) {
      Color compilerBGColor = cf.parseColor(currentWorkspace.getCompiler().getBackgroundColor());
      setBackground(compilerBGColor);
      currentWorkspace.getCompiler().setBackgroundColor(DEFAULT_COLOR_CODE); }
  }

  private void handleMultipleTurtles() {
    List<Integer> ids = new ArrayList<>(currentWorkspace.getCompiler().getAllTurtleIDs());
    List<Turtle> turtles = new ArrayList<>();
    List<Color> colors = new ArrayList<>();
    for (int i: ids) {
      Turtle t = currentWorkspace.getCompiler().getTurtleByID(i);
      turtles.add(t);
      Color c = cf.parseColor(t.getPenColorIndex());
      colors.add(c); }
    currentWorkspace.getHabitat().updateHabitat(ids, turtles, colors);
    for (int turtleId : currentWorkspace.getCompiler().getAllTurtleIDs()) {
      header.getSliders().updateImageSize(currentWorkspace, turtleId);
      header.getSliders().updatePenWidth(currentWorkspace, turtleId); }
    checkClickToActivate(currentWorkspace.getHabitat().getExistingTurtleViews());
  }

  private void checkClickToActivate(Map<Integer, TurtleView> all) {
    currentWorkspace.getHabitat().setOnMouseClicked(me -> {
      for (int id : all.keySet()) {
        if (all.get(id).contains(me.getX(), me.getY())) {
          all.get(id).setActive(!all.get(id).getActive());
          currentWorkspace.getCompiler().toggleActiveTurtle(id); } } });
  }

  private void setBackground(Color c) { currentWorkspace.getHabitat().setBackground(c); }

  private void updateColorFactory() {
    Map<Integer, int[]> colors = currentWorkspace.getCompiler().getPaletteColors();
    for (Map.Entry<Integer, int[]> e : colors.entrySet()) {
      Color color = Color.color(e.getValue()[0] / 255.0, e.getValue()[1] / 255.0,
              e.getValue()[2] / 255.0); //fixme magic val
      cf.addColor(e.getKey(), color); }
  }

  private void handleLanguage(String lang) throws FileNotFoundException {
    guiLanguage = myResources.getString(lang) + "_GUI";
    if (!guiLanguage.contains(currentLang)) {
      updateLanguage(guiLanguage); }
  }

  private void updateLanguage(String language) throws FileNotFoundException {
    currentLang = language.substring(0, language.indexOf("_"));
    myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
    imageFile = new FileSelect(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY,
        myResources.getString("ImageFile"), RESOURCES_PACKAGE + guiLanguage);
    logoFile = new FileSelect(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY,
        myResources.getString("Logo"), RESOURCES_PACKAGE + guiLanguage);
    header.getChildren().clear();
    root.getChildren().remove(header);
    setHeader();
    footer.getChildren().clear();
    root.getChildren().remove(footer);
    setFooter();
    workspaceEnvironment.getTabs().clear();
    for (int i = 1; i < workspaces.size(); i++) {
      Tab tab = new Tab(myResources.getString("Workspace") + i);
      tab.setContent(workspaces.get(i));
      workspaceEnvironment.getTabs().add(tab); }
    currentWorkspace.getCompiler().setLanguage(currentLang);
    currentWorkspace.getTerminalController().changeLanguage(currentLang);
    tabPaneController.changeLanguage(currentLang);
  }

  private void handleImageFileChooser() {
    selectButtons.clear();
    File dataFile = imageFile.getFileChooser().showOpenDialog(myStage);
    if (dataFile == null) {
      header.getButtons().setImageOff();
      return; }
    header.getButtons().setImageOff();
    for (int turtleID : currentWorkspace.getCompiler().getAllTurtleIDs()) {
      Button button = new Button("Turtle " + turtleID);
      button.setOnAction(event -> {
        currentWorkspace.getHabitat().getTurtleView(turtleID)
            .setImage("file:" + dataFile.getPath());
        header.getButtons().closeTurtleSelect(); });
      selectButtons.add(button); }
    header.getButtons().launchTurtleSelect(selectButtons);
  }

  private void handleLogoFiles() throws FileNotFoundException {
    File dataFile = logoFile.getFileChooser().showOpenDialog(myStage);
    if (dataFile == null) {
      header.getButtons().setLoadFilePressedOff();
      return; }
    header.getButtons().setLoadFilePressedOff();
    currentWorkspace.getTerminalController().sendFileInput(dataFile);
  }
}