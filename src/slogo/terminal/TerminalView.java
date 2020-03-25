package slogo.terminal;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import slogo.terminal.utils.UI.InputPanel;
import slogo.terminal.utils.UI.OutputPanel;


/**
 * terminalView generates the visualization of the terminal console.
 */
public class TerminalView extends ScrollPane {

  final static int INPUT_PANEL_HEIGHT = 40; // font-size dependent

  final static int HEADER_HEIGHT = 38;
  final static String BANNER_FILEPATH = "src/slogo/resources/banners/";
  final static String WELCOME_BANNER = "welcome_small.txt";

  private InputPanel inputPanel;
  private OutputPanel outputPanel;

  /**
   * Constructor
   */
  public TerminalView(int width, int height) {
    initializeTerminal(width, height);
  }

  public void setSize(int width, int height) {
    outputPanel.setSize(width, height - INPUT_PANEL_HEIGHT - HEADER_HEIGHT);
    inputPanel.setSize(width, INPUT_PANEL_HEIGHT);
  }

  /**
   * Sends a text string to the output panel to be displayed
   *
   * @param textLine string
   */
  public void displayTextstoOutput(String textLine) {
    outputPanel.addTexts(textLine);
  }

  /**
   * Returns the current input by the user
   *
   * @return string
   */
  public String getCurrentInput() {
    return inputPanel.getText();
  }

  /**
   * Sets the current input to a given text string
   *
   * @param str text string
   */
  public void setCurrentInput(String str) {
    inputPanel.setText(str);
  }

  /**
   * Returns the formatted string of input (adds the input code)
   *
   * @param str input string
   * @return formatted string
   */
  public String formatInput(String str) {
    return inputPanel.formatText(str);
  }

  /**
   * Returns the user input code for identification
   *
   * @return code string
   */
  public String getUSER_INPUT_CODE() {
    return inputPanel.getTERMINAL_USER_INPUT_CODE();
  }

  /**
   * Resets the content of the input panel
   */
  public void resetInputPanel() {
    inputPanel.reset();
  }

  /**
   * Gets the input area
   *
   * @return the InputArea object
   */
  public Node getInputArea() {
    return inputPanel.getInputArea();
  }

  /**
   * Gets the entire input panel
   *
   * @return the InputPanel obejct
   */
  public InputPanel getInputPanel() {
    return inputPanel;
  }

  /**
   * Gets the output panel
   *
   * @return the OutputPanel object
   */
  public OutputPanel getOutputPanel() {
    return outputPanel;
  }

  private void clearTerminal() {
    inputPanel.clearInput();
    outputPanel.clearTexts();
  }

  private void initializeTerminal(int width, int height) {
    outputPanel = new OutputPanel(width, height - INPUT_PANEL_HEIGHT - HEADER_HEIGHT);
    inputPanel = new InputPanel(width, INPUT_PANEL_HEIGHT);
    VBox box = new VBox(outputPanel, inputPanel);
    setContent(box);

    printBanner(String.format("%s%s", BANNER_FILEPATH, WELCOME_BANNER));
  }

  private void printBanner(String filePath) {
    displayTextstoOutput(readBannerfromFile(filePath));
  }

  private String readBannerfromFile(String filePath) {
    try {
      return new String(Files.readAllBytes(Paths.get(filePath)));
    } catch (IOException e) {
      System.out.println("Banner file could not be found.");
      return "Welcome to the Turtle Parser.";
    }
  }

}
