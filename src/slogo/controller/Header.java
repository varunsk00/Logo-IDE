package slogo.controller;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.workspace.Workspace;

/**
 * HeaderController.java Organizes Buttons and Sliders into a VBox and organizes popup windows
 * activated from the Header
 *
 * @author Varun Kosgi
 */
public class Header extends VBox {

  private static final String HELP_DIRECTORY = "src/slogo/resources/help/Help_";
  private static final int HELP_WINDOW_WIDTH = 400;
  private static final int HELP_WINDOW_HEIGHT = 400;
  private ResourceBundle myResources;
  private GUIButtons buttons;
  private SliderController sliders;

  /**
   * Constructor that sets Resource Bundle initializes Buttons and Sliders into VBox
   *
   * @param language the current language passed in from ParserController
   * @throws FileNotFoundException in case the File does not exist
   */
  public Header(String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    buttons = new GUIButtons(language);
    sliders = new SliderController(language);
    sliders.getVBox().getStyleClass().add("slider-box");
    getChildren().addAll(buttons.getHBox(), sliders.getVBox());
  }

  /**
   * @return ButtonController at tob of VBox
   */
  public GUIButtons getButtons() {
    return buttons;
  }

  /**
   * @return SliderController under ButtonController
   */
  public SliderController getSliders() {
    return sliders;
  }

  public void launchHelpWindow(String prompt, String language) throws IOException {
    String currentLang = language.substring(0, language.indexOf("_"));
    buttons.setHelpStatus(myResources.getString("HelpButton"));
    Stage s = new Stage();
    s.setTitle(myResources.getString(prompt));
    Text text = new Text(new String(
        Files.readAllBytes(Paths.get(HELP_DIRECTORY + prompt + "_" + currentLang + ".txt"))));
    ScrollPane root = new ScrollPane(text);
    Scene sc = new Scene(root, HELP_WINDOW_WIDTH, HELP_WINDOW_HEIGHT);
    s.setScene(sc);
    s.show();
  }

  public void launchBackgroundColorChooser(Workspace current, String lang) {
    buttons.setBackgroundColorOff();
    ColorSelect bgColorChooser = new ColorSelect(lang,
        current.getHabitat().getBackgroundColor());
    bgColorChooser.getColorPicker().setOnAction(e -> {
      current.getHabitat().setBackground(bgColorChooser.getColorPicker().getValue());
      bgColorChooser.close();
    });
    bgColorChooser.show();
  }

  public void launchPenColorChooser(Workspace current, String lang, List<Button> selection) {
    selection.clear();
    getButtons().setPenColorOff();
    ColorSelect penColorChooser = new ColorSelect(lang,
        current.getHabitat().getTurtleView(1).getPenColor());

    penColorChooser.getColorPicker().setOnAction(e -> {
      for (int turtleID : current.getCompiler().getAllTurtleIDs()) {
        Button button = new Button("Turtle " + turtleID);
        button.setOnAction(event1 -> {
          current.getHabitat().getTurtleView(turtleID)
              .setPenColor(penColorChooser.getColorPicker().getValue());
          buttons.closeTurtleSelect();
        });
        selection.add(button);
      }
      getButtons().launchTurtleSelect(selection);
      penColorChooser.close();
    });
    penColorChooser.show();
  }
}
