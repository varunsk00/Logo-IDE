package slogo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.turtle.Point;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleView;
import slogo.variable_panels.VariablesTabPaneController;
import slogo.variable_panels.VariablesTabPaneView;
import slogo.workspace.ColorFactory;
import slogo.workspace.Workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

//FIXME: DRAW TURTLE OVER LINES (CURRENTLY LINE OVER TURTLE)

//TODO(COMPLETE): CREATE VARIABLE PEN WIDTH SLIDER
//TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON
/**
 * ParserController.java
 * Sets up and runs Slogo environment as an Application
 *
 * @author Varun Kosgi
 * @author Qiaoyi Fang
 * @author Alexander Uzochukwu
 */
public class ParserController extends Application{
    private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
    private static final String IMAGE_DIRECTORY = "src/slogo/resources/images";
    private static final String LOGO_DIRECTORY = "data/examples";
    private static final String RESOURCES_PACKAGE = "slogo.resources.languages.GUI.";
    private static String guiLanguage = "English_GUI";
    private static final String IMAGE_FILE_EXTENSIONS = "*.png,*.jpg";
    private static final String LOGO_FILE_EXTENSIONS = "*.logo";
    private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + guiLanguage);

    private static final double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final double SCENE_WIDTH = 1280;
    private static final double SCENE_HEIGHT = 720;

    private static final double TABPANE_WIDTH = SCENE_WIDTH;
    private static final double TABPANE_HEIGHT = SCENE_HEIGHT/5;

    private static final Color ALL_COLOR = Color.WHITE;
    private static final int NUMBER_OF_TABS = 10;
    private static int currentTab;
    private static String currentLang = "English";

    private static FileController imageFile = new FileController(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY, myResources.getString("ImageFile"), RESOURCES_PACKAGE + guiLanguage);
    private static FileController logoFile = new FileController(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY, myResources.getString("Logo"), RESOURCES_PACKAGE + guiLanguage);

    private BorderPane root;
    private HeaderController header;

    private Stage myStage;
    private Timeline animation;
    private int DEFAULT_COLOR_CODE = -1;
    private Color DefaultColor = Color.SKYBLUE;

    private Workspace currentWorkspace;
    private List<Workspace> workspaces;

    private TabPane workspaceEnvironment;

    private VariablesTabPaneView tabPaneView;
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
     * Animation Loop and sets the Border Pane, filling it with a ButtonController, SliderController, and
     * TurtleHabitat, TerminalView, and VariablesTabPaneView
     * Sets the stage and scene and shows it
     *
     * @param args is the String[] passed in by main
     */
    public ParserController(String[] args) {
        launch(args);
    }

    /**
     *
     * @param primaryStage is the stage to display the Application, runs loop, organizes elements BorderPane
     */
    public void start(Stage primaryStage) throws FileNotFoundException {
        primaryStage.setTitle("SLogo");
        startWorkspaces();
        startAnimationLoop();
        setBorderPane();
        setTabPaneView();
        setHeader();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.show();
    }

    private void startWorkspaces(){
        workspaces = new ArrayList<>();
        workspaces.add(null);
        for(int i = 0; i< NUMBER_OF_TABS; i++){
            workspaces.add(new Workspace((SCENE_WIDTH), SCENE_HEIGHT));
        }
        currentWorkspace = workspaces.get(1);
        currentTab = 1;
        workspaceEnvironment = new TabPane();
        for(int i = 1; i < workspaces.size(); i++){
            Tab tab = new Tab(myResources.getString("Workspace") + String.valueOf(i));
            tab.setContent(workspaces.get(i));
            workspaceEnvironment.getTabs().add(tab);
        }
    }

    private void setBorderPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setMaxWidth(SCENE_WIDTH);
        root.setMaxHeight(SCENE_HEIGHT);
        root.setCenter(workspaceEnvironment);
    }

    private void setHeader() throws FileNotFoundException {
        header = new HeaderController(RESOURCES_PACKAGE + guiLanguage);
        root.setTop(header);
    }

    private void setTabPaneView() {
        tabPaneView = new VariablesTabPaneView(TABPANE_WIDTH, TABPANE_HEIGHT);
        tabPaneController = new VariablesTabPaneController(tabPaneView, currentWorkspace.getCompiler(), currentWorkspace.getTerminalController());
        root.setBottom(tabPaneView);
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try { step(); } catch (IOException ex) {
                System.out.println("Help Text File Not Found."); } });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() throws IOException {
        currentWorkspace.getTerminalController().setSize((int)myStage.getWidth()/2, (int)(myStage.getHeight()- 2*TABPANE_HEIGHT));
        currentWorkspace.getCompiler().setLanguage(currentLang);
        header.getSliders().updateZoom(currentWorkspace);
        updateTerminalBackgroundColor();
        updateCurrentWorkspace();
        updateTabPanes(false);
        handleMultipleTurtles();
        handleLanguage(header.getButtons().getLanguageStatus());
        handleButtonActions();
    }

    private void handleButtonActions() throws IOException {
        if(!header.getButtons().getHelpStatus().equals(myResources.getString("HelpButton"))){
            header.launchHelpWindow(myResources.getString(header.getButtons().getHelpStatus()), guiLanguage);}
        if(header.getButtons().getPenColorStatus()){
            header.launchPenColorChooser(currentWorkspace, RESOURCES_PACKAGE + guiLanguage, selectButtons); }
        if(header.getButtons().getBackgroundColorStatus()){
            header.launchBackgroundColorChooser(currentWorkspace, RESOURCES_PACKAGE + guiLanguage); }
        if(header.getButtons().getFileStatus()){
            handleLogoFiles(); }
        if(header.getButtons().getImageStatus()){
            handleImageFileChooser(); }
        if(header.getButtons().isViewAllTurtles()){
            currentWorkspace.getHabitat().viewTurtleInformation();
            header.getButtons().setViewAllTurtlesOff(); }
    }

    private void updateTabPanes(boolean isSwitch) {
        if (currentWorkspace.getStatus() != currentWorkspace.getTerminalController().getStatus() || isSwitch) {
            currentWorkspace.setStatus(currentWorkspace.getTerminalController().getStatus());
            tabPaneController.updateAllTables();
        }
    }

    private void updateCurrentWorkspace(){
        String workspaceString = workspaceEnvironment.getSelectionModel().getSelectedItem().getText();
        int current = currentWorkspace.getCurrentWorkspace(workspaceString);
        currentWorkspace = workspaces.get(current);
        if (current != currentTab) {
            currentTab = current;
            tabPaneController.updateCompiler(currentWorkspace.getCompiler());
            tabPaneController.updateTerminal(currentWorkspace.getTerminalController());
            updateTabPanes(true);
        }
    }

    private void updateTerminalBackgroundColor(){
        Color compilerColor = cf.parseColor(currentWorkspace.getCompiler().getBackgroundColor());
        if (!compilerColor.equals(DefaultColor)){
            setBackground(compilerColor);
            currentWorkspace.getCompiler().setBackgroundColor(DEFAULT_COLOR_CODE);
        }
    }

    private void handleMultipleTurtles(){
        for (int turtleId: currentWorkspace.getCompiler().getAllTurtleIDs()){
            currentWorkspace.getHabitat().updateHabitat(turtleId, currentWorkspace.getCompiler().getTurtleByID(turtleId));
            if(currentWorkspace.getCompiler().getTurtleByID(turtleId).isPenDown()){
                for (Point loc: currentWorkspace.getCompiler().getTurtleByID(turtleId).locationsList()) {
                    currentWorkspace.getHabitat().penDraw(currentWorkspace.getHabitat().getTurtleView(turtleId).getPenColor(), loc, turtleId);
                }
            }
            header.getSliders().updateImageSize(currentWorkspace, turtleId);
            header.getSliders().updatePenWidth(currentWorkspace, turtleId);
        }
        checkClickToActivate(currentWorkspace.getHabitat().getAll());
    }

    private void checkClickToActivate(Map<Integer, TurtleView> all){
        currentWorkspace.getHabitat().setOnMouseClicked(new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent me) {
                for(int id: all.keySet()){
                    System.out.println("YES");
                    if(all.get(id).contains(me.getX(), me.getY())){
                        System.out.println("passed if");
                        all.get(id).setActive(!all.get(id).getActive());
                        currentWorkspace.getCompiler().toggleActiveTurtle(id);
                    }
                }
            }
        });
    }

    private void setBackground(Color c){ currentWorkspace.getHabitat().setBackground(c); }

    private void handleLanguage(String lang) throws FileNotFoundException {
        guiLanguage = myResources.getString(lang) + "_GUI";
        if(!guiLanguage.contains(currentLang)){
            updateLanguage(guiLanguage);
        }
    }

    private void updateLanguage(String language) throws FileNotFoundException {
        currentLang = language.substring(0, language.indexOf("_"));
        myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
        imageFile = new FileController(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY, myResources.getString("ImageFile"), RESOURCES_PACKAGE + guiLanguage);
        logoFile = new FileController(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY, myResources.getString("Logo"), RESOURCES_PACKAGE + guiLanguage);
        header.getChildren().clear();
        root.getChildren().remove(header);
        setHeader();
        workspaceEnvironment.getTabs().clear();
        for(int i = 1; i < workspaces.size(); i++){
            Tab tab = new Tab(myResources.getString("Workspace") + String.valueOf(i));
            tab.setContent(workspaces.get(i));
            workspaceEnvironment.getTabs().add(tab);
        }
        currentWorkspace.getCompiler().setLanguage(currentLang);
        currentWorkspace.getTerminalController().changeLanguage(currentLang);
        tabPaneController.changeLanguage(currentLang);
    }

    private void handleImageFileChooser(){
        selectButtons.clear();
        File dataFile = imageFile.getFileChooser().showOpenDialog(myStage);
        if(dataFile == null){
            header.getButtons().setImageOff();
            return;
        }
        header.getButtons().setImageOff();
        for (int turtleID: currentWorkspace.getCompiler().getAllTurtleIDs()){
            Button button = new Button("Turtle " + turtleID);
            button.setOnAction(event -> {
                currentWorkspace.getHabitat().getTurtleView(turtleID).setImage("file:" + dataFile.getPath());
                header.getButtons().closeTurtleSelect();});
            selectButtons.add(button);
        }
        header.getButtons().launchTurtleSelect(selectButtons);
    }

    private void handleLogoFiles() throws FileNotFoundException {
        File dataFile = logoFile.getFileChooser().showOpenDialog(myStage);
        if(dataFile == null){
            header.getButtons().setLoadFilePressedOff();
            return;
        }
        header.getButtons().setLoadFilePressedOff();
        currentWorkspace.getTerminalController().sendFileInput(dataFile);
    }
}