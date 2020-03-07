

package slogo.controller;

import java.io.FileNotFoundException;
import java.util.ResourceBundle;
import javafx.scene.layout.VBox;
import slogo.variable_panels.VariablesTabPaneView;

public class Footer extends VBox {

  private ResourceBundle myResources;
  private DirectionalButtons directionButtons;
  private VariablesTabPaneView variableExplorer;

  public Footer(double width, double height, String language) throws FileNotFoundException {
    myResources = ResourceBundle.getBundle(language);
    directionButtons = new DirectionalButtons(language);
    variableExplorer = new VariablesTabPaneView(width, height);
    getChildren().addAll(directionButtons.getHBox(), variableExplorer);
  }

  public DirectionalButtons getButtons() {
    return directionButtons;
  }

  public VariablesTabPaneView getVariableExplorer() {
    return variableExplorer;
  }
}

