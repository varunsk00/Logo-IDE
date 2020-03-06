package slogo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.turtle.Point;
import slogo.variable_panels.VariablesTabPaneController;
import slogo.variable_panels.VariablesTabPaneView;
import slogo.workspace.ColorFactory;
import slogo.workspace.Workspace;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import java.util.List;
import java.util.ResourceBundle;

//FIXME: DRAW TURTLE OVER LINES (CURRENTLY LINES OVER TURTLE)
//FIXME: BREAK UP CLASS....REFACTOR ALMOST EVERYTHING LOL

//TODO(REQUIRED): HELP MENU IN DIFF LANGUAGES

//TODO(FUN): CREATE VARIABLE PEN WIDTH SLIDER
//TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON

public class ParserController extends Application{
    private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
    private static final String IMAGE_DIRECTORY = "src/slogo/resources/images";
    private static final String HELP_DIRECTORY = "src/slogo/resources/help/Help_";
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

    private static final double MARGIN = SCENE_WIDTH/40;

    private static final Color ALL_COLOR = Color.WHITE;
    private static final int NUMBER_OF_TABS = 10;
    private static int currentTab;
    private static String currentLang = "English";

    public FileChooser IMAGE_FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY, myResources.getString("ImageFile"));
    public FileChooser LOGO_FILE_CHOOSER = makeChooser(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY, myResources.getString("Logo"));

    private BorderPane root;
    private HeaderController header;

    private Stage myStage;
    private Timeline animation;
    private int DEFAULT_COLOR_CODE = -1;
    private Color DefaultColor = Color.SKYBLUE;
    private Color penColor = Color.BLACK;

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
     * @param primaryStage is the stage to display the Application
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
        Color compilerColor = cf.parseColor(currentWorkspace.getCompiler().getBackgroundColor());
        if (!compilerColor.equals(DefaultColor)){
            setBackground(compilerColor);
            currentWorkspace.getCompiler().setBackgroundColor(DEFAULT_COLOR_CODE);
        }
        currentWorkspace.getTerminalController().setSize((int)myStage.getWidth()/2, (int)(myStage.getHeight()- TABPANE_HEIGHT- 110-MARGIN));
        String workspaceString = workspaceEnvironment.getSelectionModel().getSelectedItem().getText();
        int current = currentWorkspace.getCurrentWorkspace(workspaceString);
        currentWorkspace = workspaces.get(current);
        for (int turtleId: currentWorkspace.getCompiler().getAllTurtleIDs()){
            currentWorkspace.getHabitat().updateHabitat(turtleId, currentWorkspace.getCompiler().getTurtleByID(turtleId));
            if(currentWorkspace.getCompiler().getTurtleByID(turtleId).isPenDown()){
                for (Point loc: currentWorkspace.getCompiler().getTurtleByID(turtleId).locationsList()) {
                    currentWorkspace.getHabitat().penDraw(currentWorkspace.getHabitat().getTurtle(turtleId).getPenColor(), loc, turtleId);
                }
            }
            header.getSliders().updateImageSize(currentWorkspace, turtleId);
        }
        handleLanguage(header.getButtons().getLanguageStatus());
        header.getSliders().updateZoom(currentWorkspace);
        if(!header.getButtons().getHelpStatus().equals(myResources.getString("HelpButton"))){
            handleHelp(header.getButtons().getHelpStatus(), guiLanguage);
        }
        if(header.getButtons().getFileStatus()){
            handleLogoFiles();
        }
        if(header.getButtons().getPenColorStatus()){
            launchPenColorChooser();
        }
        if(header.getButtons().getBackgroundColorStatus()){
            launchBackgroundColorChooser();
        }
        if(header.getButtons().getImageStatus()){
            handleImageFileChooser();
        }
        if(header.getButtons().isViewAllTurtles()){
            currentWorkspace.getHabitat().viewTurtleInformation();
            header.getButtons().setViewAllTurtlesOff();
        }
        if (current != currentTab) {
            currentTab = current;
            tabPaneController.updateCompiler(currentWorkspace.getCompiler());
            tabPaneController.updateTerminal(currentWorkspace.getTerminalController());
            updateTabPanes(true);
        }
        updateTabPanes(false);
        currentWorkspace.getCompiler().setLanguage(currentLang);
    }

    private void updateTabPanes(boolean isSwitch) {
        if (currentWorkspace.getStatus() != currentWorkspace.getTerminalController().getStatus() || isSwitch) {
            currentWorkspace.setStatus(currentWorkspace.getTerminalController().getStatus());
            tabPaneController.updateAllTables();
        }
    }

    private void setBackground(Color c){
        currentWorkspace.getHabitat().setBackground(c);
    }

    //FIXME: OFFLOAD INTO PROPERTIES FILE TO REFACTOR
    private void handleLanguage(String lang) throws FileNotFoundException {
        switch(lang){
            case "\u6c49\u8bed\u62fc\u97f3":
                guiLanguage = "Chinese_GUI";
                updateLanguage(guiLanguage);
                break;
            case "français":
                guiLanguage = "French_GUI";
                updateLanguage(guiLanguage);
                break;
            case "Deutsch":
                guiLanguage = "German_GUI";
                updateLanguage(guiLanguage);
                break;
            case "italiano":
                guiLanguage = "Italian_GUI";
                updateLanguage(guiLanguage);
                break;
            case "português":
                guiLanguage = "Portuguese_GUI";
                updateLanguage(guiLanguage);
                break;
            case "\u0070\u0443\u0441\u0441\u043a\u0438\u0439":
                guiLanguage = "Russian_GUI";
                updateLanguage(guiLanguage);
                break;
            case "español":
                guiLanguage = "Spanish_GUI";
                updateLanguage(guiLanguage);
                break;
            case "\u0939\u093f\u0902\u0926\u0940/\u0627\u0631\u062f\u0648":
                guiLanguage = "Urdu_GUI";
                updateLanguage(guiLanguage);
                break;
            case "English":
                guiLanguage = "English_GUI";
                updateLanguage(guiLanguage);
                break;
        }
    }

    //FIXME: OFFLOAD INTO PROPERTIES FILE TO REFACTOR
    private void handleHelp(String prompt, String lang) throws IOException {
        switch(prompt){
            case "Turtle Commands":
                launchHelpWindow("Turtle", lang);
                break;
            case "Math Commands":
                launchHelpWindow("Math", lang);
                break;
            case "Boolean Operations":
                launchHelpWindow("Boolean", lang);
                break;
            case "Variables, Control Structures, and User-Defined Commands":
                launchHelpWindow("Variables", lang);
                break;
        }
    }

    private void updateLanguage(String language) throws FileNotFoundException {
        currentLang = language.substring(0, language.indexOf("_"));
        myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
        IMAGE_FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY, myResources.getString("ImageFile"));
        LOGO_FILE_CHOOSER = makeChooser(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY, myResources.getString("Logo"));
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

    private void launchHelpWindow(String prompt, String language) throws IOException {
        String currentLang = language.substring(0, language.indexOf("_"));
        header.getButtons().setHelpStatus(myResources.getString("HelpButton"));
        Stage s = new Stage();
        s.setTitle(myResources.getString(prompt));
        Text text = new Text(new String(Files.readAllBytes(Paths.get(HELP_DIRECTORY + prompt + "_" + currentLang + ".txt"))));
        ScrollPane root = new ScrollPane(text);
        Scene sc = new Scene(root, 400, 400);
        s.setScene(sc);
        s.show();
    }

    private void launchPenColorChooser() {
        selectButtons.clear();
        header.getButtons().setPenColorOff();
        ColorController penColorChooser = new ColorController(RESOURCES_PACKAGE + guiLanguage, penColor);
        penColorChooser.getColorPicker().setOnAction(e -> {
            for (int turtleID: currentWorkspace.getCompiler().getAllTurtleIDs()){
                Button button = new Button("Turtle " + turtleID);
                button.setOnAction(event1 -> currentWorkspace.getHabitat().getTurtle(turtleID).setPenColor(penColorChooser.getColorPicker().getValue()));
                selectButtons.add(button);
            }
            header.getButtons().chooserPane(selectButtons);
            penColorChooser.close();
        });
        penColorChooser.show();
    }

    private void launchBackgroundColorChooser() {
        header.getButtons().setBackgroundColorOff();
        ColorController bgColorChooser = new ColorController(RESOURCES_PACKAGE + guiLanguage, currentWorkspace.getHabitat().getBackgroundColor());
        bgColorChooser.getColorPicker().setOnAction(e -> {
            setBackground(bgColorChooser.getColorPicker().getValue());
            bgColorChooser.close();});
        bgColorChooser.show();
    }

    private static FileChooser makeChooser(String extensionsAccepted, String directory, String type) {
        String[] extensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        result.setInitialDirectory(new File(System.getProperty("user.dir"),directory));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter(type, extensions));
        return result;
    }

    private void handleImageFileChooser(){
        selectButtons.clear();
        File dataFile = IMAGE_FILE_CHOOSER.showOpenDialog(myStage);
        if(dataFile == null){
            header.getButtons().setImageOff();
            return;
        }
        header.getButtons().setImageOff();
        for (int turtleID: currentWorkspace.getCompiler().getAllTurtleIDs()){
            Button button = new Button("Turtle " + turtleID);
            button.setOnAction(event -> currentWorkspace.getHabitat().getTurtle(turtleID).setImage("file:" + dataFile.getPath()));

            selectButtons.add(button);
        }
        header.getButtons().chooserPane(selectButtons);
    }

    private void handleLogoFiles() throws FileNotFoundException {
        File dataFile = LOGO_FILE_CHOOSER.showOpenDialog(myStage);
        if(dataFile == null){
            header.getButtons().setLoadFilePressedOff();
            return;
        }
        header.getButtons().setLoadFilePressedOff();
        currentWorkspace.getTerminalController().sendFileInput(dataFile);
    }
}