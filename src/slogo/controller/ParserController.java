package slogo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import org.reflections.Reflections;
import slogo.compiler.parser.Compiler;
import slogo.terminal.TerminalController;
import slogo.terminal.TerminalView;
import slogo.turtle.Point;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleHabitat;
import slogo.turtle.TurtleView;
import slogo.variable_panels.VariablesTabPaneController;
import slogo.variable_panels.VariablesTabPaneView;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;

//FIXME: replace JAVA FILENOTFOUND EXCEPTION WITH comp.executeFile()
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
    private static String GUI_LANGUAGE = "English_GUI";
    private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + GUI_LANGUAGE);

    private static double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private double SCENE_WIDTH = 1280;
    private double SCENE_HEIGHT = 720;

    private double TABPANE_WIDTH = SCENE_WIDTH;
    private double TABPANE_HEIGHT = 150;

    private static final Color ALL_COLOR = Color.WHITE;

    private static final String IMAGE_FILE_EXTENSIONS = "*.png,*.jpg";
    private static final String LOGO_FILE_EXTENSIONS = "*.logo";

    public FileChooser IMAGE_FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY);
    public FileChooser LOGO_FILE_CHOOSER = makeChooser(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY);

    private BorderPane root;

    private VBox header = new VBox();
    private ButtonController buttons;
    private SliderController sliders;

    private Stage myStage;
    private Timeline animation;
    private Color backgroundColor = Color.WHITE;
    private Color penColor = Color.BLACK;

    private Workspace currentWorkspace;
    private Workspace turtleWorkspace1;
    private Workspace turtleWorkspace2;

    private TabPane workspaceEnvironment;

    private VariablesTabPaneView tabPaneView;
    private VariablesTabPaneController tabPaneController;

    private ArrayList<Button> selectButtons = new ArrayList<>();
    /**
     * Empty Constructor Needed to run the application due to Application requirements Not called
     * explicitly in code
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
        //primaryStage.setMaximized(true);
        startWorkspaces();
        startAnimationLoop();
        setBorderPane();
        setTabPaneView();
        setHeader();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.setResizable(false);
        myStage.show();
    }

    private void startWorkspaces(){
        turtleWorkspace1 = new Workspace(SCENE_WIDTH, SCENE_HEIGHT);
        currentWorkspace = turtleWorkspace1;
        workspaceEnvironment = new TabPane();
        Tab tab1 = new Tab();
        tab1.setText("Workspace 1");
        tab1.setContent(turtleWorkspace1);

        turtleWorkspace2 = new Workspace(SCENE_WIDTH, SCENE_HEIGHT);
        Tab tab2 = new Tab();
        tab2.setText("Workspace 2");
        tab2.setContent(turtleWorkspace2);

        workspaceEnvironment.getTabs().addAll(tab1, tab2);
    }

    private void setBorderPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setMaxWidth(SCENE_WIDTH);
        root.setMaxHeight(SCENE_HEIGHT);
        root.setCenter(workspaceEnvironment);
    }

    private void setHeader() throws FileNotFoundException {
        buttons = new ButtonController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders = new SliderController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders.getVBox().getStyleClass().add("slider-box");
        header.getChildren().addAll(buttons.getHBox(), sliders.getVBox());
        root.setTop(header);
    }

    private void setTabPaneView() {
        tabPaneView = new VariablesTabPaneView(TABPANE_WIDTH, TABPANE_HEIGHT);
        tabPaneController = new VariablesTabPaneController(tabPaneView, currentWorkspace.getCompiler(), currentWorkspace.getTerminalController());
        root.setBottom(tabPaneView);
    }

    //FIXME: BIG NO NO!! REMOVE PRINTSTACKTRACE IMMEDIATELY
    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step();
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() throws IOException {
        String workspaceString = workspaceEnvironment.getSelectionModel().getSelectedItem().getText();
        int current = Integer.parseInt(workspaceString.substring(workspaceString.length()-1));
//        currentWorkspace = turtleWorkspace2;
        //FIXME: PARSE INT AS INSTANCE VARIABLE DON'T HARDCODE
        if(current == 1){
            currentWorkspace = turtleWorkspace1;
        }
        else if(current ==2){
            currentWorkspace = turtleWorkspace2;
        }
        for (int turtleId: currentWorkspace.getCompiler().getAllTurtleIDs()){
            currentWorkspace.getHabitat().updateHabitat(turtleId, currentWorkspace.getCompiler().getTurtleByID(turtleId));
            if(currentWorkspace.getCompiler().getTurtleByID(turtleId).isPenDown()){
                for (Point loc: currentWorkspace.getCompiler().getTurtleByID(turtleId).locationsList()) {
                    currentWorkspace.getHabitat().penDraw(currentWorkspace.getHabitat().getTurtle(turtleId).getPenColor(), loc, turtleId);
                }
            }
            updateImageSize(turtleId);
        }
        handleLanguage(buttons.getLanguageStatus());
        updateZoom();
        if(!buttons.getHelpStatus().equals(myResources.getString("HelpButton"))){
            handleHelp(buttons.getHelpStatus(), GUI_LANGUAGE);
            //launchHelpWindow(buttons.getHelpStatus(), buttons.getLanguageStatus());
        }
        if(buttons.getFileStatus()){
            handleLogoFiles();
        }
        if(buttons.getPenColorStatus()){
            launchPenColorChooser();
        }
        if(buttons.getBackgroundColorStatus()){
            launchBackgroundColorChooser();
        }
        if(buttons.getImageStatus()){
            handleImageFileChooser();
        }
        setGlobalBackground(backgroundColor);
        updateTabPanes();
    }

    private void updateTabPanes() {
        if (currentWorkspace.getStatus() != currentWorkspace.getTerminalController().getStatus()) {
            currentWorkspace.setStatus(currentWorkspace.getTerminalController().getStatus());
            tabPaneController.updateAllTables();
        }
    }

    private void setGlobalBackground(Color c){
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        currentWorkspace.getHabitat().setBackground(backgroundColor);
    }

    //FIXME: OFFLOAD INTO PROPERTIES FILE TO REFACTOR
    private void handleLanguage(String lang) throws FileNotFoundException {
        switch(lang){
            case "\u6c49\u8bed\u62fc\u97f3":
                GUI_LANGUAGE = "Chinese_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "français":
                GUI_LANGUAGE = "French_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "Deutsch":
                GUI_LANGUAGE = "German_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "italiano":
                GUI_LANGUAGE = "Italian_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "português":
                GUI_LANGUAGE = "Portuguese_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "\u0070\u0443\u0441\u0441\u043a\u0438\u0439":
                GUI_LANGUAGE = "Russian_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "español":
                GUI_LANGUAGE = "Spanish_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "\u0939\u093f\u0902\u0926\u0940/\u0627\u0631\u062f\u0648":
                GUI_LANGUAGE = "Urdu_GUI";
                updateLanguage(GUI_LANGUAGE);
                break;
            case "English":
                GUI_LANGUAGE = "English_GUI";
                updateLanguage(GUI_LANGUAGE);
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
        String currentLang = language.substring(0, language.indexOf("_"));
        myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
        IMAGE_FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY);
        LOGO_FILE_CHOOSER = makeChooser(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY);
        header.getChildren().clear();
        root.getChildren().remove(header);
        setHeader();
        currentWorkspace.getCompiler().setLanguage(currentLang);
        currentWorkspace.getTerminalController().changeLanguage(currentLang);
        tabPaneController.changeLanguage(currentLang);
    }

    private void launchHelpWindow(String prompt, String language) throws IOException {
        String currentLang = language.substring(0, language.indexOf("_"));
        buttons.setHelpStatus(myResources.getString("HelpButton"));
        Stage s = new Stage();
        s.setTitle(myResources.getString(prompt));
        Text text = new Text(new String(Files.readAllBytes(Paths.get(HELP_DIRECTORY + prompt + "_" + currentLang + ".txt"))));
        ScrollPane root = new ScrollPane(text);
        Scene sc = new Scene(root, 400, 400);
        s.setScene(sc);
        s.show();
    }

    //FIXME: Refactor following two methods
    private void launchPenColorChooser() {
        selectButtons.clear();
        buttons.setPenColorOff();
        Stage s = new Stage();
        s.setTitle(myResources.getString("ColorWindow"));
        TilePane r = new TilePane();
        ColorPicker cp = new ColorPicker();
        EventHandler<ActionEvent> event = e -> {
            for (int turtleID: currentWorkspace.getCompiler().getAllTurtleIDs()){
                Button button = new Button("Turtle " + turtleID);
                button.setOnAction(event1 -> currentWorkspace.getHabitat().getTurtle(turtleID).setPenColor(cp.getValue()));
                selectButtons.add(button);
            }
            chooserPane(selectButtons);
            s.close();
        };
        cp.setValue(penColor);
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
    }

    private void launchBackgroundColorChooser() {
        buttons.setBackgroundColorOff();
        Stage s = new Stage();
        s.setTitle(myResources.getString("ColorWindow"));
        TilePane r = new TilePane();
        ColorPicker cp = new ColorPicker();
        EventHandler<ActionEvent> event = e -> {
            backgroundColor = cp.getValue();
            s.close();
        };
        cp.setValue(backgroundColor);
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
    }

    private static FileChooser makeChooser(String extensionsAccepted, String directory) {
        String[] extensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        result.setInitialDirectory(new File(System.getProperty("user.dir"),directory));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter(myResources.getString("ImageFile"), extensions));
        return result;
    }

    private void handleImageFileChooser(){
        selectButtons.clear();
        File dataFile = IMAGE_FILE_CHOOSER.showOpenDialog(myStage);
        if(dataFile == null){
            buttons.setImageOff();
            return;
        }
        buttons.setImageOff();

        for (int turtleID: currentWorkspace.getCompiler().getAllTurtleIDs()){
            Button button = new Button("Turtle " + turtleID);
            button.setOnAction(event -> currentWorkspace.getHabitat().getTurtle(turtleID).setFill(new ImagePattern(new Image("file:" + dataFile.getPath()))));
            selectButtons.add(button);
        }
        chooserPane(selectButtons);
    }

    private void chooserPane(List<Button> turtleButtons){
        Stage s = new Stage();
        Pane root = new Pane();
        Scene sc = new Scene(root, 200, 200);
        ListView<Button> turtleOptions = new ListView<Button>();
        for (Button button: turtleButtons){
            turtleOptions.getItems().addAll(button);
        }
        root.getChildren().addAll(turtleOptions);
        s.setScene(sc);
        s.show();
    }

    //FIXME: directly call comp.executeFile, and run in the terminal (maybe add method to TerminalView)
    private void handleLogoFiles() throws FileNotFoundException {
        File dataFile = LOGO_FILE_CHOOSER.showOpenDialog(myStage);
        String input = "";
        if(dataFile == null){
            buttons.setLoadFilePressedOff();
            return;
        }
        buttons.setLoadFilePressedOff();
        Scanner scanner = new Scanner(dataFile);
        while (scanner.hasNext()){
            String line = scanner.nextLine();
            if(line.startsWith("#")) {
                continue;
            }
            input += line + " ";
        }
        currentWorkspace.getTerminal().setCurrentInput(input);
    }

    private void updateZoom(){
        currentWorkspace.getHabitat().setScaleX(sliders.getZoom()/3.0);
        currentWorkspace.getHabitat().setScaleY(sliders.getZoom()/3.0);
    }

    private void updateImageSize(int turtleId){
        currentWorkspace.getHabitat().getTurtle(turtleId).setScaleX(sliders.getSizeValue()/3.0);
        currentWorkspace.getHabitat().getTurtle(turtleId).setScaleY(sliders.getSizeValue()/3.0);
    }

//    private void setWorkspace(int tabNumber){
//        String workspace = "turtleworkspace" + String.valueOf(tabNumber);
//        this.currentWorkspace = workspace;
//    }
}