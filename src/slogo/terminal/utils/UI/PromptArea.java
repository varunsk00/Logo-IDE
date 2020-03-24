package slogo.terminal.utils.UI;

import javafx.scene.control.TextArea;

/**
 * PromptArea generates the prompt are (literally, the ">>>").
 * @author Qiaoyi Fang
 */
public class PromptArea extends TextArea {

  private static int PREF_MARGIN_COUNT = 4;
  private static String defaultPromptText = ">>>";

  /**
   * Constructor
   *
   */
  public PromptArea() {
    super(defaultPromptText);

    setEditable(false);
    setStyle("-fx-font-family: \"Consolas\";");
    changeSize();
  }

  private void changeSize() {
    setPrefColumnCount(PREF_MARGIN_COUNT);
  }

}
