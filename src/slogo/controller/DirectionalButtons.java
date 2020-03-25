package slogo.controller;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import slogo.workspace.Workspace;

/**
 * DirectionalButtons.java
 * Organizes the four directional Turtle Movement Buttons into an HBox
 *
 * @author Varun Kosgi
 */
public class DirectionalButtons {

  private ResourceBundle myResources;
  private boolean rightPressed;
  private boolean leftPressed;
  private boolean upPressed;
  private boolean downPressed;
  private HBox myButtons;

  /**
   * Constructor that sets Resource Bundle and initializes all initial states of buttons
   * Button states are initially False; ComboBox states have a defined initial String
   *
   * @param language the current language passed in from ParserController
   * @throws FileNotFoundException in case the File does not exist
   */
  public DirectionalButtons(String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    this.rightPressed = false;
    this.leftPressed = false;
    this.downPressed = false;
    this.upPressed = false;
    renderButtons();
  }

  /**
   * @return the JavaFX HBox that contains all the buttons
   */
  public HBox getHBox() {
    return myButtons;
  }

  /**
   * @return state of the Up Button
   */
  public boolean getUpPressed() {
    return upPressed;
  }

  /**
   * Sets the Up Button to off
   */
  public void setUpOff() {
    upPressed = false;
  }

  /**
   * Calls the UpCommand on the current Workspace's compiler
   * @param current workspace to be acted upon
   */
  public void executeUp(Workspace current) {
    current.getCompiler().execute(myResources.getString("UpCommand"));
    setUpOff();
  }

  /**
   * @return state of the Down Button
   */
  public boolean getDownPressed() {
    return downPressed;
  }

  /**
   * Sets the Down Button to off
   */
  public void setDownOff() {
    downPressed = false;
  }

  /**
   * Calls the DownCommand on the current Workspace's compiler
   * @param current workspace to be acted upon
   */
  public void executeDown(Workspace current) {
    current.getCompiler().execute(myResources.getString("DownCommand"));
    setDownOff();
  }

  /**
   * @return state of the Right Button
   */
  public boolean getRightPressed() {
    return rightPressed;
  }

  /**
   * Sets the Right Button to off
   */
  public void setRightOff() {
    rightPressed = false;
  }

  /**
   * Calls the RightCommand on the current Workspace's compiler
   * @param current workspace to be acted upon
   */
  public void executeRight(Workspace current) {
    current.getCompiler().execute(myResources.getString("RightCommand"));
    setRightOff();
  }

  /**
   * @return state of the Left Button
   */
  public boolean getLeftPressed() {
    return leftPressed;
  }

  /**
   * Sets the Left Button to off
   */
  public void setLeftOff() {
    leftPressed = false;
  }

  /**
   * Calls the LeftCommand on the current Workspace's compiler
   * @param current workspace to be acted upon
   */
  public void executeLeft(Workspace current) {
    current.getCompiler().execute(myResources.getString("LeftCommand"));
    setLeftOff();
  }

  private void renderButtons() { // Creates and initializes all Buttons based on Regex Values
    myButtons = new HBox();
    Button UpButton = makeButton("UpButton", event -> upPressed = true);
    Button DownButton = makeButton("DownButton", event -> downPressed = true);
    Button LeftButton = makeButton("LeftButton", event -> leftPressed = true);
    Button RightButton = makeButton("RightButton", event -> rightPressed = true);
    myButtons.getChildren().addAll(UpButton, DownButton, RightButton, LeftButton);
    formatButton(UpButton);
    formatButton(DownButton);
    formatButton(RightButton);
    formatButton(LeftButton);
  }

  private Button makeButton(String key, EventHandler e) {
    Button tempButton = new Button(myResources.getString(key));
    tempButton.setMaxWidth(Double.MAX_VALUE);
    tempButton.setOnAction(e);
    return tempButton;
  }

  private void formatButton(Button tempButton) {
    myButtons.setHgrow(tempButton, Priority.ALWAYS);
  }
}

