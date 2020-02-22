package slogo.controller;
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
import slogo.Main;

import java.util.ResourceBundle;


public class ParserController extends Application{
    private static final String STYLESHEET = "parser_parser_team06/src/slogo/slogo.resources/default.css";
    private static final String RESOURCES_PACKAGE = "slogo.slogo.resources";
    private static final String GUI_LANGUAGE = "English_GUI";
    private static ResourceBundle myResources = ResourceBundle.getBundle(GUI_LANGUAGE);

    private static double FRAMES_PER_SECOND = 30;
    private static final double SECOND_DELAY = 1.0 / FRAMES_PER_SECOND;

    private static final double SCENE_WIDTH = 1000;
    private static final double SCENE_HEIGHT = 500;

    private static final Color GRID_BACKGROUND = Color.BEIGE;
    private static final Color ALL_COLOR = Color.ALICEBLUE;

    private BorderPane root;
    private HBox center;
    private ButtonController header;
    private Stage myStage;
    private Timeline animation;

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
        setBorderPane();
        setHeader();
        Scene scene = new Scene(root, SCENE_WIDTH, SCENE_HEIGHT);
        myStage = primaryStage;
        myStage.setScene(scene);
        myStage.show();
    }

    private void setBorderPane() {
        root = new BorderPane();
        root.setBackground(new Background(new BackgroundFill(ALL_COLOR, CornerRadii.EMPTY, Insets.EMPTY)));
        root.setMaxWidth(SCENE_WIDTH);
        root.setMaxHeight(SCENE_WIDTH);
    }

    private void setHeader() {
        header = new ButtonController(GUI_LANGUAGE);
        root.setTop(header.getHeader());
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
            Color c = cp.getValue();
            // set text of the label to RGB value of color
            System.out.println("Pen Color: Red = " + c.getRed() + ", Green = " + c.getGreen()
                    + ", Blue = " + c.getBlue());
            s.close();
        };
        // set listener
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
            Color c = cp.getValue();
            root.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
            s.close();
        };
        // set listener
        cp.setOnAction(event);
        r.getChildren().add(cp);
        Scene sc = new Scene(r, 200, 200);
        s.setScene(sc);
        s.show();
    }
}
