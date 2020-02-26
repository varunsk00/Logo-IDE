package slogo.controller;
import slogo.compiler.Compiler;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleHabitat;
import slogo.terminal.TerminalView;
import slogo.terminal.TerminalController;

import javafx.scene.image.Image;
import javafx.scene.paint.ImagePattern;
import javafx.stage.FileChooser;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import java.io.File;
import java.util.ResourceBundle;

//TODO(REQUIRED): SYSTEM-WIDE LANGUAGE SWITCHING
//TODO(FUN): CREATE VARIABLE PEN WIDTH SLIDER
//TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON
//TODO(FUN): RESIZE TURTLE SLIDER

public class ParserController extends Application{
    private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
    private static final String IMAGE_DIRECTORY = "src/slogo/resources/images";
    private static final String RESOURCES_PACKAGE = "slogo.resources.languages.";
    private static String GUI_LANGUAGE = "English_GUI";
    private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + GUI_LANGUAGE);

    private static double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final double SCENE_WIDTH = 1280;
    private static final double SCENE_HEIGHT = 720;

    private static final double HABITAT_WIDTH = SCENE_WIDTH/2;
    private static final double HABITAT_HEIGHT = SCENE_HEIGHT;

    private static final Color ALL_COLOR = Color.ALICEBLUE;
    private static final String IMAGE_FILE_EXTENSIONS = "*.png,*.jpg";

    public FileChooser FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS);
    private BorderPane root;
    private ButtonController header;
    private Stage myStage;
    private Timeline animation;
    private Color backgroundColor = Color.WHITE;
    private Color penColor = Color.BLACK;

    private TerminalView term;
    private TerminalController term_controller;

    private TurtleHabitat myHabitat;
    private Turtle myTurtle1 = new Turtle();

    private Compiler comp;

    /**
     * Empty Constructor Needed to run the application due to Application requirements Not called
     * explicitly in code
     */
    public ParserController() {
    }

    /**
     * Constructor used in Main to begin the program Begins our JavaFX application Starts the
     * Animation Loop and sets the Border Pane, filling it with a ButtonControls, SliderControls, and
     * SimulationViews Sets the stage and scene and shows it
     *
     * @param args is the String[] passed in by main
     */
    public ParserController(String[] args) {
        launch(args);
    }

    /**
     * Start method for the Application Sets the BorderPane and fills it with ButtonControls (in the
     * header) and SliderControls (in the Footer) Sets the Center with SimulationView objects to
     * represent the Simulation Initializes in nested methods
     *
     * @param primaryStage is the stage to display the Application
     */
    public void start(Stage primaryStage) {
        primaryStage.setTitle("SLogo");
        startAnimationLoop();
        startCompiler();
        setBorderPane();
        setTurtleHabitat();
        setTerminalView();
        setHeader();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.setResizable(false);
        myStage.show();
    }

    private void setBorderPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setMaxWidth(SCENE_WIDTH);
        root.setMaxHeight(SCENE_WIDTH);
    }

    private void setHeader() {
        header = new ButtonController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        root.setTop(header.getHeader());
    }

    private void setTerminalView() {
        term = new TerminalView( (int) SCENE_WIDTH/2, (int) SCENE_HEIGHT);
        term_controller = new TerminalController(term);
        term_controller.setCompiler(comp);
        root.setLeft(term);
    }

    private void setTurtleHabitat() {
        myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT);
        myHabitat.getTurtleHabitat().getStyleClass().add("habitat");
        root.setRight(myHabitat.getTurtleHabitat());
    }

    private void startCompiler(){
        comp = new Compiler();
        comp.addTurtle("Turtle 1", myTurtle1);
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> step());
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() {
        handleLanguage(header.getLanguageStatus());
        if(header.getPenColorStatus()){
            launchPenColorChooser();
        }
        if(header.getBackgroundColorStatus()){
            launchBackgroundColorChooser();
        }
        if(header.getImageStatus()){
            handleFileChooser();
        }
        if (header.getHelpStatus()) {
        }
        if(myTurtle1.isPenDown()){
            myHabitat.penDraw(penColor, myTurtle1.getXLocation(), myTurtle1.getYLocation());
        }
        myHabitat.setBackground(backgroundColor);
        myHabitat.getTurtle().updateTurtleView(myTurtle1);
        root.setRight(myHabitat.getTurtleHabitat());
    }

    //FIXME: OFFLOAD INTO PROPERTIES FILE TO REFACTOR
    private void handleLanguage(String lang){
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

    private void updateLanguage(String language){
        String currentLang = language.substring(0, language.indexOf("_"));
        myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + language);
        FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS);
        setHeader();
        comp.setLanguage(currentLang);
        term_controller.changeLanguage(currentLang);
    }

    private void launchPenColorChooser() {
        header.setPenColorOff();
        Stage s = new Stage();
        s.setTitle(myResources.getString("ColorWindow"));
        TilePane r = new TilePane();
        ColorPicker cp = new ColorPicker();
        // create a event handler
        EventHandler<ActionEvent> event = e -> {
            penColor = cp.getValue();
            s.close();
        };
        // set listener
        cp.setValue(penColor);
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
    }

    private void launchBackgroundColorChooser() {
        header.setBackgroundColorOff();
        Stage s = new Stage();
        s.setTitle(myResources.getString("ColorWindow"));
        TilePane r = new TilePane();
        ColorPicker cp = new ColorPicker();
        // create a event handler
        EventHandler<ActionEvent> event = e -> {
            // color
            backgroundColor = cp.getValue();
            s.close();
        };
        // set listener
        cp.setValue(backgroundColor);
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
    }

//    private void createColorPicker(Color variable){
//        Stage s = new Stage();
//        s.setTitle(myResources.getString("ColorWindow"));
//        TilePane r = new TilePane();
//        ColorPicker cp = new ColorPicker();
//        // create a event handler
//        EventHandler<ActionEvent> event = e -> {
//            variable = cp.getValue();
//            s.close();
//        };
//        // set listener
//        cp.setValue(variable);
//        cp.setOnAction(event);
//        r.getChildren().add(cp);
//        Scene sc = new Scene(r, 200, 200);
//        s.setScene(sc);
//        s.show();
//    }

    private static FileChooser makeChooser(String extensionsAccepted) {
        String[] extensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        // pick Image Directory to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir"),IMAGE_DIRECTORY));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter(myResources.getString("ImageFile"), extensions[0], extensions[1]));
        return result;
    }

    private void handleFileChooser(){
        File dataFile = FILE_CHOOSER.showOpenDialog(myStage);
        if(dataFile == null){
            header.setImageOff();
            return;
        }
        header.setImageOff();
        myHabitat.getTurtle().setFill(new ImagePattern(new Image("file:" + dataFile.getPath())));
    }
}