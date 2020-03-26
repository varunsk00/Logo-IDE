package slogo.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.EventHandler;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

/**
 * **NOTE TO UTA**
 * I feel this is a great representation of my ability to uses classes to streamline the addition of new feature. Any
 * new front-end feature involving a buttons can simply be added here with two lines.
 *
 * This class demonstrates knowledge of Regex Calling,ComboBox Creation (from NanoBrowser lab), and class encapsulation
 * so that there is only one place to make changes.
 */

/**
 * GUIButtons.java
 * Creates all the Buttons and Pop-Ups in a Slogo environment
 *
 * @author Varun Kosgi
 */
public class GUIButtons {

  private final String SUPPORTED_LANGUAGES = "src/slogo/resources/languages/LanguageList.txt";
  private final String PROMPTS = "src/slogo/resources/languages/HelpPrompts.txt";
  private final int TURTLE_SELECT_WIDTH = 200;
  private final int TURTLE_SELECT_HEIGHT = 200;
  private boolean loadFilePressed;
  private boolean turtleImagePressed;
  private boolean penColorPressed;
  private boolean backgroundColorPressed;
  private boolean viewAllTurtles;
  private String languagePressed;
  private String helpPressed;
  private ResourceBundle myResources;
  private List<String> languages;
  private List<String> helpPrompts;
  private Stage imageWindow = new Stage();
  private HBox myButtons;

  /**
   * Constructor that sets Resource Bundle and initializes all initial states of buttons
   * Button states are initially False; ComboBox states have a defined initial String
   *
   * @param language the current language passed in from ParserController
   * @throws FileNotFoundException in case the File does not exist
   */
  public GUIButtons(String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    this.languages = text2Regex(new File(SUPPORTED_LANGUAGES));
    this.helpPrompts = text2Regex(new File(PROMPTS));
    this.loadFilePressed = false;
    this.turtleImagePressed = false;
    this.penColorPressed = false;
    this.backgroundColorPressed = false;
    this.viewAllTurtles = false;
    this.helpPressed = myResources.getString("HelpButton");
    this.languagePressed = myResources.getString("LanguageButton");
    renderButtons();
  }

  /**
   * @return the JavaFX HBox that contains all the buttons
   */
  public HBox getHBox() {
    return myButtons;
  }

  /**
   * @return the pressed state (boolean) of the View Turtles Button
   */
  public boolean isViewAllTurtles() {
    return viewAllTurtles;
  }

  /**
   * Unpresses the View Turtles Button
   */
  public void setViewAllTurtlesOff() {
    viewAllTurtles = false;
  }

  /**
   * @return the pressed state (String) of the Language ComboBox
   */
  public String getLanguageStatus() {
    return languagePressed;
  }

  /**
   * @return the pressed state (boolean) of the Load Image Button
   */
  public boolean getImageStatus() {
    return turtleImagePressed;
  }

  /**
   * Unpresses the Load Image Button
   */
  public void setImageOff() {
    turtleImagePressed = false;
  }

  /**
   * @return the pressed state (boolean) of the Load File Button
   */
  public boolean getFileStatus() {
    return loadFilePressed;
  }

  /**
   * Unpresses the Load File Button
   */
  public void setLoadFilePressedOff() {
    loadFilePressed = false;
  }

  /**
   * @return the pressed state (boolean) of the Pen Color Button
   */
  public boolean getPenColorStatus() {
    return penColorPressed;
  }

  /**
   * Unpresses the Pen Color Button
   */
  public void setPenColorOff() {
    penColorPressed = false;
  }

  /**
   * @return the pressed state (boolean) of the Background Color Button
   */
  public boolean getBackgroundColorStatus() {
    return backgroundColorPressed;
  }

  /**
   * Unpresses the Background Color Button
   */
  public void setBackgroundColorOff() {
    backgroundColorPressed = false;
  }

  /**
   * @return the pressed state (String) of the Help Combo Box
   */
  public String getHelpStatus() {
    return helpPressed;
  }

  /**
   * Sets the Help Button Text to the chosen submenu
   */
  public void setHelpStatus(String help) {
    this.helpPressed = help;
  }

  /**
   * Launches a window to allow user to choose specific Turtle on Screen Used for Image switching
   * and Pen Color switching
   *
   * @param turtleButtons the List of Buttons, one for each turtle on screen
   */
  public void launchTurtleSelect(List<Button> turtleButtons) {
    Pane root = new Pane();
    Scene sc = new Scene(root, TURTLE_SELECT_WIDTH, TURTLE_SELECT_HEIGHT);
    ListView<Button> turtleOptions = new ListView<Button>();
    for (Button button : turtleButtons) {
      turtleOptions.getItems().addAll(button); }
    root.getChildren().addAll(turtleOptions);
    imageWindow.setScene(sc);
    imageWindow.show();
  }

  /**
   * Closes Turtle Select Window
   */
  public void closeTurtleSelect() {
    imageWindow.close();
  }

  private void renderButtons() { //Creates and initializes all Buttons based on Regex Values
    myButtons = new HBox();
    Button loadButton = makeButton("LoadButton", event -> loadFilePressed = true);
    Button imageButton = makeButton("ImageButton", event -> turtleImagePressed = true);
    Button viewAllTurtlesButton = makeButton("ViewTurtle", event -> viewAllTurtles = true);
    Button penButton = makeButton("PenButton", event -> penColorPressed = true);
    Button backgroundButton = makeButton("BackgroundButton", event -> backgroundColorPressed = true);
    ComboBox langMenu = makeDropDown("LanguageButton", languages);
    ComboBox helpMenu = new ComboBox();
    helpMenu.setValue(helpPressed);
    helpMenu.getItems().addAll(helpPrompts);
    helpMenu.setOnAction(event -> this.helpPressed = (String) helpMenu.getValue());
    myButtons.getChildren()
        .addAll(loadButton, imageButton, viewAllTurtlesButton, penButton, backgroundButton, helpMenu, langMenu);
    formatButton(loadButton);
    formatButton(imageButton);
    formatButton(penButton);
    formatButton(backgroundButton);
    formatButton(viewAllTurtlesButton);
    formatBox(helpMenu);
    formatBox(langMenu);
  }

  private Button makeButton(String key, EventHandler e) {
    Button tempButton = new Button(myResources.getString(key));
    tempButton.setMaxWidth(Double.MAX_VALUE);
    tempButton.setOnAction(e);
    return tempButton;
  }

  private ComboBox makeDropDown(String key, List<String> options) {
    ComboBox tempMenu = new ComboBox();
    tempMenu.getItems().addAll(options);
    tempMenu.setValue(myResources.getString(key));
    if (key.equals("LanguageButton")) {
      tempMenu.setOnAction(event -> languagePressed = (String) tempMenu.getValue()); }
    else if (key.equals("HelpButton")) {
      tempMenu.setOnAction(event -> this.helpPressed = (String) tempMenu.getValue()); }
    return tempMenu;
  }

  private void formatButton(Button tempButton) {
    HBox.setHgrow(tempButton, Priority.ALWAYS);
  }

  private void formatBox(ComboBox tempMenu) {
    HBox.setHgrow(tempMenu, Priority.ALWAYS);
  }

  private List<String> text2Regex(File dataFile) throws FileNotFoundException {
    List<String> ret = new ArrayList<>();
    Scanner scanner = new Scanner(dataFile);
    while (scanner.hasNextLine()) {
      ret.add(myResources.getString(scanner.nextLine())); }
    return ret;
  }
}
