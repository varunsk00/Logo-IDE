package slogo.controller;

import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.util.Duration;
import slogo.compiler.Compiler;
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

    private double SCENE_WIDTH;
    private double SCENE_HEIGHT;
    private double HEADER_HEIGHT = 80;

    private double HABITAT_WIDTH = SCENE_WIDTH/2;
    private double HABITAT_HEIGHT = SCENE_HEIGHT;

    private double TERMINAL_WIDTH = SCENE_WIDTH/2;
    private double TERMINAL_HEIGHT = SCENE_HEIGHT;

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

    private TerminalView term;
    private TerminalController term_controller;
    private int status;

    private VariablesTabPaneView tabPaneView;
    private VariablesTabPaneController tabPaneController;

    private TurtleHabitat myHabitat;
    private Turtle myTurtle1 = new Turtle();

    private Compiler comp;

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
        changeScreenSizetoMax();
        startAnimationLoop();
        startCompiler();
        setBorderPane();
        setTurtleHabitat();
        setTerminalView();
        setTabPaneView();
        setHeader();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.setResizable(false);
        myStage.show();
    }

    private void changeScreenSizetoMax(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        SCENE_WIDTH = 1280;
        SCENE_HEIGHT = 720;
        TABPANE_WIDTH = SCENE_WIDTH;
        TABPANE_HEIGHT = 150;
        HABITAT_WIDTH = SCENE_WIDTH/2;
        HABITAT_HEIGHT = SCENE_HEIGHT - HEADER_HEIGHT - TABPANE_HEIGHT;
        TERMINAL_WIDTH = SCENE_WIDTH/2;
        TERMINAL_HEIGHT = SCENE_HEIGHT - HEADER_HEIGHT - TABPANE_HEIGHT;

    }

    private void setBorderPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setMaxWidth(SCENE_WIDTH);
        root.setMaxHeight(SCENE_HEIGHT);
    }

    private void setHeader() throws FileNotFoundException {
        buttons = new ButtonController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders = new SliderController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders.getVBox().getStyleClass().add("slider-box");
        header.getChildren().addAll(buttons.getHBox(), sliders.getVBox());
        root.setTop(header);
    }

    private void setTerminalView() {
        term = new TerminalView( (int) TERMINAL_WIDTH, (int) TERMINAL_HEIGHT);
        term_controller = new TerminalController(term);
        term_controller.setExternals(comp);
        status = -1;
        root.setLeft(term);
    }

    private void setTabPaneView() {
        tabPaneView = new VariablesTabPaneView(TABPANE_WIDTH, TABPANE_HEIGHT);
        tabPaneController = new VariablesTabPaneController(tabPaneView, comp, term_controller);
        root.setBottom(tabPaneView);
    }

    private void setTurtleHabitat() {
        myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT);
        root.setRight(myHabitat.getTurtleHabitat());
    }

    private void startCompiler(){
        comp = new Compiler();
        comp.addTurtle("Turtle 1", myTurtle1);
        comp.addTurtle("Turtle 2", new Turtle());
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
        for (String turtleId: comp.getAllTurtleIDs()){
            myHabitat.updateHabitat(turtleId, comp.getTurtleByID(turtleId));
            if(comp.getTurtleByID(turtleId).isPenDown()){
                for (Point loc: comp.getTurtleByID(turtleId).locationsList()) {
                    myHabitat.penDraw(myHabitat.getTurtle(turtleId).getPenColor(), loc, turtleId);
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
            handleLogoFileChooser();
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
        //System.out.println(status);
        //System.out.println(term_controller.getStatus());
        if (status != term_controller.getStatus()) {
            status = term_controller.getStatus();
            tabPaneController.updateAllTables();
        }
    }

    private void setGlobalBackground(Color c){
        root.setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
        myHabitat.setBackground(backgroundColor);
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
        comp.setLanguage(currentLang);
        term_controller.changeLanguage(currentLang);
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
            penColor = cp.getValue();
            s.close();
        };
        cp.setValue(penColor);
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
        for (String turtleID: comp.getAllTurtleIDs()){
            Button button = new Button(turtleID);
            button.setOnAction(event1 -> myHabitat.getTurtle(turtleID).setPenColor(penColor));
            selectButtons.add(button);
        }
        chooserPane(selectButtons);
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

        for (String turtleID: comp.getAllTurtleIDs()){
            Button button = new Button(turtleID);
            button.setOnAction(event -> myHabitat.getTurtle(turtleID).setFill(new ImagePattern(new Image("file:" + dataFile.getPath()))));
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
    private void handleLogoFileChooser() throws FileNotFoundException {
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
        term.setCurrentInput(input);
    }

    private void updateZoom(){
        myHabitat.getTurtleHabitat().setScaleX(sliders.getZoom()/3.0);
        myHabitat.getTurtleHabitat().setScaleY(sliders.getZoom()/3.0);
    }

    private void updateImageSize(String turtleId){
        myHabitat.getTurtle(turtleId).setScaleX(sliders.getSizeValue()/3.0);
        myHabitat.getTurtle(turtleId).setScaleY(sliders.getSizeValue()/3.0);
    }
}