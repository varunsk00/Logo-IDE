

package slogo.controller;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import javafx.event.EventHandler;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import slogo.workspace.Workspace;

public class DirectionalButtons {

  private ResourceBundle myResources;
  private boolean rightPressed;
  private boolean leftPressed;
  private boolean upPressed;
  private boolean downPressed;
  private HBox myButtons;

  /**
   * Constructor that sets Resource Bundle and initializes all initial states of buttons      *
   * * Button states are initially False; ComboBox states have a defined initial String      *
   * * @param language the current language passed in from ParserController      * @throws
   * FileNotFoundException in case the File does not exist
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

  public boolean getUpPressed() {
    return upPressed;
  }

  public void setUpOff() {
    upPressed = false;
  }

  public void executeUp(Workspace current) {
    current.getCompiler().execute(myResources.getString("UpCommand"));
    setUpOff();
  }

  public boolean getDownPressed() {
    return downPressed;
  }

  public void setDownOff() {
    downPressed = false;
  }

  public void executeDown(Workspace current) {
    current.getCompiler().execute(myResources.getString("DownCommand"));
    setDownOff();
  }

  public boolean getRightPressed() {
    return rightPressed;
  }

  public void setRightOff() {
    rightPressed = false;
  }

  public void executeRight(Workspace current) {
    current.getCompiler().execute(myResources.getString("RightCommand"));
    setRightOff();
  }

  public boolean getLeftPressed() {
    return leftPressed;
  }

  public void setLeftOff() {
    leftPressed = false;
  }

  public void executeLeft(Workspace current) {
    current.getCompiler().execute(myResources.getString("LeftCommand"));
    setLeftOff();
  }

  /**
   * Creates and initializes all Buttons based on Regex Values
   */
  private void renderButtons() {
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

