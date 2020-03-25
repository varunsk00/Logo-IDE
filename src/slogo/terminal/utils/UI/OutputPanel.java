package slogo.terminal.utils.UI;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import slogo.terminal.utils.textLines.TestLine;

/**
 * Output Panel is responsible for the display of the user-entered commands and feedback information
 * from the compiler.
 */
public class OutputPanel extends ListView<String> {

  private final static String CSS_FILEPATH = "slogo/resources/styleSheets/output_panel.css";
  private final static double DISPLAY_MARGIN = 15;
  private final static int UNDO_ENTRY_NUM = 2;
  private final static int EMPTY_THRESHOLD = 1;

  /**
   * Constructor
   *
   * @param width      width
   * @param max_height max height (if extends this height, the user could access to previous content
   *                   by the scroll bar)
   */
  public OutputPanel(int width, int max_height) {
    super(FXCollections.observableArrayList());

    //disable focus
    //setMouseTransparent(true);
    setFocusTraversable(false);

    setMinWidth(width);
    setMaxWidth(width);
    setPrefWidth(width);
    setPrefHeight(max_height);
    setMaxHeight(max_height); // grows with the list

    getStylesheets().add(CSS_FILEPATH);

    setCellFactory(listView -> new TestLine(width - DISPLAY_MARGIN));
  }

  public void setSize(int width, int height) {
    setMinWidth(width);
    setMaxWidth(width);
    setPrefWidth(width);
    setPrefHeight(height);
    setMaxHeight(height);
  }

  /**
   * Deletes the last entry (compiler message and user input) from terminal display
   */
  public void undoEntry() {
    int num = UNDO_ENTRY_NUM;
    while (getItems().size() > EMPTY_THRESHOLD && num != 0) {
      num--;
      getItems().remove(getItems().size() - 1);
    }
  }

  /**
   * Adds a new line of text
   *
   * @param str text string
   */
  public void addTexts(String str) {
    getItems().add(str);
    scrollTo(getItems().size());
  }

  /**
   * Resets the screen
   */
  public void clearTexts() {
    getItems().clear();
  }

  /**
   * [unimplemented] Returns the selected highlight texts
   *
   * @return text string
   */
  public String getSelectedText() {
    return null;
  }
}
