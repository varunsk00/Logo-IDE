package slogo.controller;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.workspace.Workspace;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;

public class HeaderController extends VBox {
    private static final String HELP_DIRECTORY = "src/slogo/resources/help/Help_";
    private ResourceBundle myResources;
    private ButtonController buttons;
    private SliderController sliders;

    public HeaderController(String language) throws FileNotFoundException {
        myResources = ResourceBundle.getBundle(language);
        buttons = new ButtonController(language);
        sliders = new SliderController(language);
        sliders.getVBox().getStyleClass().add("slider-box");
        getChildren().addAll(buttons.getHBox(), sliders.getVBox());
    }

    public ButtonController getButtons(){
        return buttons;
    }

    public SliderController getSliders(){ return sliders; }

    public void launchHelpWindow(String prompt, String language) throws IOException {
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

    public void launchBackgroundColorChooser(Workspace current, String lang) {
        buttons.setBackgroundColorOff();
        ColorController bgColorChooser = new ColorController(lang, current.getHabitat().getBackgroundColor());
        bgColorChooser.getColorPicker().setOnAction(e -> {
            current.getHabitat().setBackground(bgColorChooser.getColorPicker().getValue());
            bgColorChooser.close();});
        bgColorChooser.show();
    }

    public void launchPenColorChooser(Workspace current, String lang, List<Button> selection) {
        selection.clear();
        getButtons().setPenColorOff();
        ColorController penColorChooser = new ColorController(lang, current.getHabitat().getTurtleView(1).getPenColor());
        penColorChooser.getColorPicker().setOnAction(e -> {
            for (int turtleID: current.getCompiler().getAllTurtleIDs()){
                Button button = new Button("Turtle " + turtleID);
                button.setOnAction(event1 -> {current.getHabitat().getTurtleView(turtleID).setPenColor(penColorChooser.getColorPicker().getValue());
                                            buttons.closeTurtleSelect();});
                selection.add(button);
            }
            getButtons().launchTurtleSelect(selection);
            penColorChooser.close();});
        penColorChooser.show();
    }
}
