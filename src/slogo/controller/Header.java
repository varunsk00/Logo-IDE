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
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import slogo.workspace.Workspace;

/**
 * Header.java
 * Organizes Buttons and Sliders into a VBox and organizes popup windows
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
  private Sliders sliders;

  /**
   * Constructor that sets Resource Bundle and initializes Buttons/Sliders into VBox
   *
   * @param language the current language passed in from ParserController
   * @throws FileNotFoundException in case the File does not exist
   */
  public Header(String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    buttons = new GUIButtons(language);
    sliders = new Sliders(language);
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
  public Sliders getSliders() {
    return sliders;
  }

  /**
   * Launches new JavaFX Stage to display contents of Help Text File
   *
   * @param prompt selected SubMenu
   * @param language current language
   * @throws IOException in case File cannot be read
   */
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

  /**
   * Launches new Color Select Window for background color
   *
   * @param current workspace to be modified
   * @param lang current GUI Language
   */
  public void launchBackgroundColorChooser(Workspace current, String lang) {
    buttons.setBackgroundColorOff();
    ColorSelect bgColorChooser = new ColorSelect(lang,
        current.getHabitat().getBackgroundColor());
    bgColorChooser.getColorPicker().setOnAction(e -> {
      current.getHabitat().setBackground(bgColorChooser.getColorPicker().getValue());
      bgColorChooser.close(); });
    bgColorChooser.show();
  }

  /**
   * Launches new Color Select Window for Pen color
   *
   * @param current workspace to be modified
   * @param lang current GUI Language
   */
  public void launchPenColorChooser(Workspace current, String lang, List<Button> selection) {
    selection.clear();
    getButtons().setPenColorOff();
    ColorSelect penColorChooser = new ColorSelect(lang, current.getHabitat().getTurtleView(1).getPenColor());
    penColorChooser.getColorPicker().setOnAction(e -> {
      for (int turtleID : current.getCompiler().getAllTurtleIDs()) {
        Button button = new Button("Turtle " + turtleID);
        selection.add(button);
        button.setOnAction(event1 -> {
          Color c = penColorChooser.getColorPicker().getValue();
          current.setTurtlePenColor(turtleID, c);
          buttons.closeTurtleSelect(); }); }
      getButtons().launchTurtleSelect(selection);
      penColorChooser.close(); });
    penColorChooser.show();
  }
}
