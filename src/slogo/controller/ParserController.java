package slogo.controller;
import javafx.geometry.Rectangle2D;
import javafx.stage.Screen;
import slogo.compiler.Compiler;
import slogo.turtle.Point;
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
import slogo.variable_panels.VariablesTabPaneController;
import slogo.variable_panels.VariablesTabPaneView;

import java.io.File;
import java.io.FileNotFoundException;
import java.nio.file.Files;
import java.util.ResourceBundle;
import java.util.Scanner;
import java.util.stream.Stream;

//FIXME: replace JAVA FILENOTFOUND EXCEPTION WITH comp.executeFile()
//FIXME: DRAW TURTLE OVER LINES (CURRENTLY LINES OVER TURTLE)

//TODO(REQUIRED): HELP MENU IN DIFF LANGUAGES

//TODO(FUN): Slider labels in diff languages
//TODO(FUN): CREATE VARIABLE PEN WIDTH SLIDER
//TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON
//TODO(FUN): ADD REGEX FOR ZOOM AND SIZE IN OTHER LANGUAGES

public class ParserController extends Application{
    private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
    private static final String IMAGE_DIRECTORY = "src/slogo/resources/images";
    private static final String LOGO_DIRECTORY = "data/examples";
    private static final String RESOURCES_PACKAGE = "slogo.resources.languages.";
    private static String GUI_LANGUAGE = "English_GUI";
    private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + GUI_LANGUAGE);

    private static double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private double SCENE_WIDTH = 1280;
    private double SCENE_HEIGHT = 720;
    private double HEADER_HEIGHT = 45;

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
    private boolean status;

    private VariablesTabPaneView tabPaneView;
    private VariablesTabPaneController tabPaneController;

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

        SCENE_WIDTH = screenBounds.getMaxX();
        SCENE_HEIGHT = screenBounds.getMaxY();

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
        root.setMaxHeight(SCENE_WIDTH);
    }

    private void setHeader() {
        buttons = new ButtonController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders = new SliderController(RESOURCES_PACKAGE + GUI_LANGUAGE);
        sliders.getVBox().getStyleClass().add("slider-box");
        header.getChildren().addAll(buttons.getHBox(), sliders.getVBox());
        root.setTop(header);
    }

    private void setTerminalView() {
        term = new TerminalView( (int) SCENE_WIDTH/2, (int) SCENE_HEIGHT);
        term_controller = new TerminalController(term);
        term_controller.setExternals(comp);
        status = false;
        root.setLeft(term);
    }

    private void setTabPaneView() {
        tabPaneView = new VariablesTabPaneView(TABPANE_WIDTH, TABPANE_HEIGHT);
        tabPaneController = new VariablesTabPaneController(tabPaneView, comp, term_controller);
        root.setBottom(tabPaneView);
    }

    private void setTurtleHabitat() {
        myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT, HEADER_HEIGHT);
        myHabitat.getTurtleHabitat().getStyleClass().add("habitat");
        root.setRight(myHabitat.getTurtleHabitat());
    }

    private void startCompiler(){
        comp = new Compiler();
        comp.addTurtle("Turtle 1", myTurtle1);
    }

    private void startAnimationLoop() {
        KeyFrame frame = new KeyFrame(Duration.seconds(SECOND_DELAY), e -> {
            try {
                step();
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        animation = new Timeline();
        animation.setCycleCount(Timeline.INDEFINITE);
        animation.getKeyFrames().add(frame);
        animation.play();
    }

    private void step() throws FileNotFoundException {
        handleLanguage(buttons.getLanguageStatus());
        updateZoom();
        updateImageSize();
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
        if (buttons.getHelpStatus()) {
        }
        if(myTurtle1.isPenDown()){
            for (Point loc: myTurtle1.locationsList()) {
                myHabitat.penDraw(penColor, loc.getX(), loc.getY());
            }
        }
        myHabitat.setBackground(backgroundColor);
        myHabitat.getTurtle().updateTurtleView(myTurtle1);

        root.setCenter(myHabitat.getTurtleHabitat());
        updateTabPanes();
    }

    private void updateTabPanes() {
        if (status != term_controller.getStatus()) {
            status = term_controller.getStatus();
            tabPaneController.updateAllTables();
            System.out.println("change");
        }
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
        IMAGE_FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS, IMAGE_DIRECTORY);
        LOGO_FILE_CHOOSER = makeChooser(LOGO_FILE_EXTENSIONS, LOGO_DIRECTORY);

        header.getChildren().clear();
        root.getChildren().remove(header);
        setHeader();

        comp.setLanguage(currentLang);
        term_controller.changeLanguage(currentLang);
    }

    //FIXME: Refactor following two methods
    private void launchPenColorChooser() {
        buttons.setPenColorOff();
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
        buttons.setBackgroundColorOff();
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

    private static FileChooser makeChooser(String extensionsAccepted, String directory) {
        String[] extensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        // pick Image Directory to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir"),directory));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter(myResources.getString("ImageFile"), extensions));
        return result;
    }

    private void handleImageFileChooser(){
        File dataFile = IMAGE_FILE_CHOOSER.showOpenDialog(myStage);
        if(dataFile == null){
            buttons.setImageOff();
            return;
        }
        buttons.setImageOff();
        myHabitat.getTurtle().setFill(new ImagePattern(new Image("file:" + dataFile.getPath())));
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

    private void updateImageSize(){
        myHabitat.getTurtle().setScaleX(sliders.getSizeValue()/3.0);
        myHabitat.getTurtle().setScaleY(sliders.getSizeValue()/3.0);
    }
}