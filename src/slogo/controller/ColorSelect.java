package slogo.controller;

import java.util.ResourceBundle;
import javafx.scene.Scene;
import javafx.scene.control.ColorPicker;
import javafx.scene.layout.TilePane;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

/**
 * ColorSelect.java
 * Pop-Up Window to select a Color
 *
 * @author Varun Kosgi
 */
public class ColorSelect extends Stage {

  private static final int WINDOW_HEIGHT = 200;
  private static final int WINDOW_WIDTH = 200;
  private static ResourceBundle myResources;
  private TilePane tp;
  private ColorPicker cp;

  /**
   * Initializes ColorPicker Window
   * @param lang current GUI language
   * @param color currently selected color
   */
  public ColorSelect(String lang, Color color) {
    setResizable(false);
    myResources = ResourceBundle.getBundle(lang);
    setTitle(myResources.getString("ColorWindow"));
    tp = new TilePane();
    cp = new ColorPicker();
    cp.setValue(color);
    tp.getChildren().add(cp);
    Scene sc = new Scene(tp, WINDOW_WIDTH, WINDOW_HEIGHT);
    setScene(sc);
  }

  /**
   * @return current ColorPicker
   */
  public ColorPicker getColorPicker() {
    return cp;
  }

  /**
   * @return user selected color
   */
  public Color getColor() {
    return cp.getValue();
  }
}
