package slogo.controller;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import slogo.variable_panels.VariablesTabPaneView;
/**
 * Footer.java
 * Organizes Directional Buttons and Variable Explorer into a VBox
 *
 * @author Varun Kosgi
 */
public class Footer extends VBox {

  private ResourceBundle myResources;
  private DirectionalButtons directionButtons;
  private VariablesTabPaneView variableExplorer;

  /**
   * Constructor that initializes Footer
   * @param width width of Footer
   * @param height height of Footer
   * @param language current GUI Language
   * @throws FileNotFoundException if file isn't found
   */
  public Footer(double width, double height, String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    directionButtons = new DirectionalButtons(language);
    variableExplorer = new VariablesTabPaneView(width, height);
    getChildren().addAll(directionButtons.getHBox(), variableExplorer);
  }

  /**
   * @return Directional Buttons that can move Turtle
   */
  public DirectionalButtons getButtons() {
    return directionButtons;
  }

  /**
   * @return Variable Explorer
   */
  public VariablesTabPaneView getVariableExplorer() {
    return variableExplorer;
  }
}

