package slogo.controller;
import javafx.geometry.Rectangle2D;
import javafx.scene.shape.Rectangle;
import javafx.stage.Screen;
import slogo.compiler.Compiler;
import slogo.turtle.Turtle;
import slogo.turtle.TurtleHabitat;
import slogo.terminal.TerminalView;
import slogo.terminal.TerminalController;
import slogo.compiler.*;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
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
import java.io.FileNotFoundException;
import java.sql.SQLOutput;
import java.util.ResourceBundle;
import java.util.Scanner;


public class ParserController extends Application{
    private static final String STYLESHEET = "slogo/resources/styleSheets/default.css";
    private static final String IMAGE_DIRECTORY = "slogo/resources/images";
    private static final String RESOURCES_PACKAGE = "slogo.resources.languages.";
    private static final String GUI_LANGUAGE = "English_GUI";
    private static ResourceBundle myResources = ResourceBundle.getBundle(RESOURCES_PACKAGE + GUI_LANGUAGE);

    private static double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final int SCENE_HEADER = 25;
    //private static final double SCENE_WIDTH = 1280;
    //private static final double SCENE_HEIGHT = 720;

    //private static final double HABITAT_WIDTH = SCENE_WIDTH/2;
    //private static final double HABITAT_HEIGHT = SCENE_HEIGHT;

    private double SCENE_WIDTH = 1280;
    private double SCENE_HEIGHT = 720;

    private double HABITAT_WIDTH = SCENE_WIDTH/2;
    private double HABITAT_HEIGHT = SCENE_HEIGHT;


    private static final Color GRID_BACKGROUND = Color.BEIGE;
    private static final Color ALL_COLOR = Color.ALICEBLUE;
    private static final String IMAGE_FILE_EXTENSIONS = "*.png,*.jpg";

    public static final FileChooser FILE_CHOOSER = makeChooser(IMAGE_FILE_EXTENSIONS);
    private BorderPane root;
    private HBox center;
    private ButtonController header;
    private Stage myStage;
    private Timeline animation;
    private Color backgroundColor = Color.WHITE;
    private Color penColor = Color.BLACK;

    private TerminalView term;
    private TerminalController term_controller;

    private TurtleHabitat myHabitat;
    private Turtle myTurtle1 = new Turtle();
    private boolean t = true;
    Rectangle r = new Rectangle(10,10);

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
        primaryStage.setMaximized(true);
        changeSceneSize();
        startAnimationLoop();
        startCompiler();
        setBorderPane();
        setHeader();
        setTurtleHabitat();
        setTerminalView();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        scene.getStylesheets().add(STYLESHEET);

        //FIXME: WASD FOR TESTING; EVENTUALLY REMOVE
        scene.setOnKeyPressed(e -> {
            try {
                handleKeyInput(e.getCode());
            } catch (FileNotFoundException ex) {
                ex.printStackTrace();
            }
        });
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.setResizable(false);
        myStage.show();
    }

    private void changeSceneSize(){
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        SCENE_WIDTH = screenBounds.getMaxX();
        SCENE_HEIGHT = screenBounds.getMaxY() ;

        HABITAT_WIDTH = SCENE_WIDTH/2;
        HABITAT_HEIGHT = SCENE_HEIGHT;
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
        term = new TerminalView( (int) SCENE_WIDTH/2, (int) SCENE_HEIGHT - SCENE_HEADER);
        term_controller = new TerminalController(term);
        term_controller.setCompiler(comp);
        root.setLeft(term);
    }

    private void setTurtleHabitat() {
        myHabitat = new TurtleHabitat(HABITAT_WIDTH, HABITAT_HEIGHT);
        myHabitat.getTurtleHabitat().getStyleClass().add("habitat");
        myHabitat.getTurtle().setX(HABITAT_WIDTH/2);
        myHabitat.getTurtle().setY(HABITAT_HEIGHT/2);
        root.setCenter(myHabitat.getTurtleHabitat());
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
            String[] commands = {"fd fd 50"};
            for(int i = 0; i < commands.length; i++) {
                String var = comp.execute(commands[i]);
                //myHabitat.updateHabitat(penColor);
                System.out.println(var);
            }
            header.setHelpOff();
        }
        myHabitat.getTurtle().updateTurtleView(myTurtle1);
        root.setCenter(myHabitat.getTurtleHabitat());
        term_controller.changeLanguage(header.getLanguageStatus());
        System.out.println(header.getLanguageStatus());
    }

    private void launchPenColorChooser() {
        header.setPenColorOff();
        Stage s = new Stage();
        s.setTitle(myResources.getString("ColorWindow"));
        TilePane r = new TilePane();
        ColorPicker cp = new ColorPicker();
        // create a event handler
        EventHandler<ActionEvent> event = e -> {
            // color
            penColor = cp.getValue();
            // set text of the label to RGB value of color
            System.out.println("Pen Color: Red = " + penColor.getRed() + ", Green = " + penColor.getGreen()
                    + ", Blue = " + penColor.getBlue());
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
            myHabitat.getTurtleHabitat().setBackground(new Background(new BackgroundFill(backgroundColor, CornerRadii.EMPTY, Insets.EMPTY)));
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

    private static FileChooser makeChooser(String extensionsAccepted) {
        String[] extensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        // pick a reasonable place to start searching for files
        result.setInitialDirectory(new File(System.getProperty("user.dir"),"src/slogo/resources/images"));
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
        System.out.println(dataFile.getPath());
        myHabitat.getTurtle().setFill(new ImagePattern(new Image("file:" + dataFile.getPath())));
    }

    public void handleKeyInput (KeyCode code) throws FileNotFoundException {
        if(code == KeyCode.W){
            myHabitat.getTurtle().setRotate(0);
            myHabitat.getTurtle().setY(myHabitat.getTurtle().getY()-1); }
        if(code == KeyCode.A){
            myHabitat.getTurtle().setRotate(-90);
            myHabitat.getTurtle().setX(myHabitat.getTurtle().getX()-1); }
        if(code == KeyCode.S){
            myHabitat.getTurtle().setRotate(180);
            myHabitat.getTurtle().setY(myHabitat.getTurtle().getY()+1); }
        if(code == KeyCode.D){
            myHabitat.getTurtle().setRotate(90);
            myHabitat.getTurtle().setX(myHabitat.getTurtle().getX()+1); }
    }

}
