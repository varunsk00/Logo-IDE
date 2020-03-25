package slogo.controller;

import java.util.ResourceBundle;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;
import slogo.workspace.Workspace;

/**
 * Sliders.java
 * Organizes Turtle size, Zoom, and Pen Width Sliders for Slogo Environment
 *
 * @author Varun Kosgi
 * @author Alexander Uzochukwu
 */
public class Sliders {

  private static final int MIN_ZOOM = 1;
  private static final int DEFAULT_ZOOM = 3;
  private static final int MAX_ZOOM = 5;
  private static final int MIN_SIZE = 1;
  private static final int DEFAULT_SIZE = 3;
  private static final int MAX_SIZE = 5;
  private static final int MIN_PEN_WIDTH = 2;
  private static final int DEFAULT_PEN_WIDTH = 2;
  private static final int MAX_PEN_WIDTH = 5;
  private static ResourceBundle myResources;
  private static Slider zoom;
  private static Slider imagesize;
  private static Slider penWidth;
  private static VBox sliders;

  /**
   * Constructor that sets the Captions Languages and generates Sliders
   * @param language the input GUI Language
   */
  public Sliders(String language) {
    this.myResources = ResourceBundle.getBundle(language);
    renderSliders();
  }

  /**
   * @return the Zoom Slider's value
   */
  public double getZoom() {
    return zoom.getValue();
  }

  /**
   * @return the Image Size Slider's value
   */
  public double getSizeValue() {
    return imagesize.getValue();
  }

  /**
   * @return the Pen Width Slider's value
   */
  public double getPenWidth() {
    return penWidth.getValue();
  }

  /**
   * @return the JavaFX VBox that contains all sliders
   */
  public VBox getVBox() {
    return sliders;
  }

  /**
   * Scales the TurtleHabitat
   * @param current workspace to be Zoomed
   */
  public void updateZoom(Workspace current) {
    current.getHabitat().setScaleX(getZoom() / 3.0);
    current.getHabitat().setScaleY(getZoom() / 3.0);
  }

  /**
   * Scales the TurtleView
   * @param current workspace to be modified
   * @param turtleId current TurtleView
   */
  public void updateImageSize(Workspace current, int turtleId) {
    current.getHabitat().getTurtleView(turtleId).setScaleX(getSizeValue() / 3.0);
    current.getHabitat().getTurtleView(turtleId).setScaleY(getSizeValue() / 3.0);
  }

  /**
   * Sets Pen Width
   * @param current workspace to be modified
   * @param turtleId current TurtleView
   */
  public void updatePenWidth(Workspace current, int turtleId) {
    if (current.getHabitat().getTurtle(turtleId).getIsActive()) {
      current.getHabitat().getTurtle(turtleId).setPenSize(getPenWidth()); }
  }

  private void renderSliders() {
    sliders = new VBox();
    HBox allLabels = new HBox();
    addLabel("SizeSlider", allLabels);
    addLabel("ZoomSlider", allLabels);
    addLabel("PenSlider", allLabels);
    HBox allSliders = new HBox();
    imagesize = addAndReturnSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE, allSliders);
    zoom = addAndReturnSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM, allSliders);
    penWidth = addAndReturnSlider(MIN_PEN_WIDTH, MAX_PEN_WIDTH, DEFAULT_PEN_WIDTH, allSliders);
    sliders.getChildren().add(allLabels);
    sliders.getChildren().add(allSliders);
  }

  private void addLabel(String key, HBox text) {
    Label tempLabel = new Label(myResources.getString(key));
    tempLabel.setMaxWidth(Double.MAX_VALUE);
    tempLabel.setAlignment(Pos.CENTER);
    HBox.setHgrow(tempLabel, Priority.ALWAYS);
    text.getChildren().add(tempLabel);
  }

  private Slider addAndReturnSlider(int min, int max, int def, HBox sliders) {
    Slider tempSlider = new Slider(min, max, def);
    HBox.setHgrow(tempSlider, Priority.ALWAYS);
    setSliderTicks(tempSlider);
    sliders.getChildren().add(tempSlider);
    return tempSlider;
  }

  private void setSliderTicks(Slider slider) {
    slider.setBlockIncrement(1);
    slider.setMajorTickUnit(1);
    slider.setMinorTickCount(0);
    slider.setShowTickLabels(true);
    slider.setSnapToTicks(true);
  }
}

