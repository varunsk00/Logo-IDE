package slogo.turtle;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

/**
 * TurtleView.java
 *
 * A JavaFX Rectangle that visualizes a Turtle object
 *
 * @author Varun Kosgi
 * @author Alexander Uzochukwu
 */
public class TurtleView extends Rectangle {
  private final String SHAPEMAP_RESOURCE = "slogo.resources.shape.shape_map";
  private final double RECTANGLE_RATIO = 1.426;
  private final Color COLOR = Color.GREEN;
  private final Color GREY = Color.GREY;
  private String image_filepath = "slogo/resources/images/turtle_green.png";
  private String extension = image_filepath.substring(image_filepath.indexOf("."));
  private String CIRCLE_TYPE = "CIRCLE";
  private String RECTANGLE_TYPE = "RECT"; // 1.0 width & 1.5 width
  private String DEFAULT_TYPE = "CIRCLE";
  private String SQUARE_TYPE = "SQUARE"; // 1.0 width & 1.0 height
  private double xOffset;
  private double yOffset;
  private double shapeWidth;
  private double shapeHeight;
  private double penWidth;
  private boolean cleared;
  private boolean isActive;
  private boolean prevActive;
  private boolean isIMG;
  private boolean updated;
  private Image img;
  private Color penColor = Color.BLACK;

  /**
   * Constructor that initializes the width and height of the TurtleView, in addition to converting Slogo Coordinates
   * to Scene Coordinates
   * @param width the desired width of the TurtleView
   * @param height the desired height of the TurtleView
   * @param habitatWidth the width of the TurtleHabitat (used for coordinate conversion)
   * @param habitatHeight the height of the TurtleHabitat (used for coordinate conversion)
   */
  public TurtleView(double width, double height, double habitatWidth, double habitatHeight) {
    super(width, height);
    shapeWidth = width;
    shapeHeight = height;
    this.xOffset = habitatWidth / 2 - getWidth() / 2;
    this.yOffset = habitatHeight / 2 - getHeight();
    isIMG = true;
    this.img = new Image(image_filepath);
    penWidth = 2.0;
    isActive = true;
    prevActive = true;
  }

  /**
   * Updates the TurtleView based on constantly changing states of the Turtle object
   * @param turtle the Turtle object associated with the TurtleView
   */
  public void updateTurtleView(Turtle turtle) {
    setRotate(turtle.getHeading());
    setX(turtle.getXLocation() + xOffset);
    setY(turtle.getYLocation() + yOffset);
    setVisible(turtle.isShowTurtle());
    penWidth = turtle.getPenSize();
    cleared = turtle.isCleared();
    if (prevActive != isActive) {
      turtle.setActive(isActive); }
    if (cleared) {
      turtle.setCleared(false);
      turtle.handleClear(); }
    if (!turtle.getIsActive()) {
      setTurtleViewGray(); }
    if (turtle.getIsActive()) {
      setTurtleViewColor(); }
    prevActive = isActive;
  }

  /**
   * @returns true if the TurtleView has been assigned an image
   */
  public boolean isIMG() {
    return isIMG;
  }

  /**
   * @return the width of the TurtleView
   */
  public double getShapeWidth() {
    return shapeWidth;
  }

  /**
   * @return the height of the TurtleView
   */
  public double getShapeHeight() {
    return shapeHeight;
  }

  /**
   * @return the ImagePattern (Rectangle Fill) associated with the Image of the TurtleView
   */
  public ImagePattern getImage() {
    return new ImagePattern(img);
  }

  /**
   * Sets the current Image of the TurtleView's Rectangle
   * @param filepath
   */
  public void setImage(String filepath) {
    isIMG = true;
    image_filepath = filepath;
    setFill(new ImagePattern(new Image(filepath)));
  }

  /**
   * Sets the Shape of the TurtleView based on the ShapeID associated with the TurtleView
   * @param shapeID the shapeID to be checked in the dictionary
   * @param isColor checks if the TurtleView has an associated color
   */
  public void setShape(int shapeID, boolean isColor) {
    isIMG = false;
    List<Map.Entry<String, String>> shapeDict = loadDict(SHAPEMAP_RESOURCE);
    for (Map.Entry<String, String> entry : shapeDict) {
      if (String.valueOf(shapeID).equals(entry.getKey())) {
        setShapeFactory(entry.getValue(), isColor);
        return; } }
    setShapeFactory(DEFAULT_TYPE, isColor);
    System.out.println("Error: Unimplemented shape type");
  }

  /**
   * @return color of the Pen associated with TurtleView
   */
  public Color getPenColor() {
    return penColor;
  }

  /**
   * Sets a new color of the Pen
   * @param inputColor new color
   */
  public void setPenColor(Color inputColor) {
    penColor = inputColor;
  }

  /**
   * @return the width of the Pen
   */
  public double getPenWidth() {
    return penWidth;
  }

  /**
   * Sets a new width for the Pen behind this TurtleView
   * @param width the new width of the Pen
   */
  public void setPenWidth(double width) {
    penWidth = width;
  }

  /**
   * @return the state of the TurtleView (active is true; inactive is false)
   */
  public boolean getActive() {
    return isActive;
  }

  /**
   * Sets state of the Turtle
   * @param b the new state
   */
  public void setActive(boolean b) {
    isActive = b;
  }

  /**
   * @return the x-coordinate offset from Slogo Coordinates to JavaFX coordinates
   */
  public double getXOffset() {
    return xOffset;
  }

  /**
   * @return the y-coordinate offset from Slogo Coordinates to JavaFX coordinates
   */
  public double getYOffset() {
    return yOffset;
  }

  /**
   * @return if the clearScreen command has been called on the TurtleView
   */
  public boolean isCleared() {
    return cleared;
  }

  /**
   * @return if the TurtleView has been updates based on changes in the associated Turtle
   */
  public boolean isUpdated() {
    return updated;
  }

  /**
   * Set the state of the TurtleView upon each update of the Turtle object
   * @param updated new state
   */
  public void setUpdated(boolean updated) {
    this.updated = updated;
  }

  private void setShapeFactory(String shapeType, boolean isColor) {
    if (shapeType.equals(SQUARE_TYPE)) {
      setSquareSize();
      System.out.println(getColor(isColor));
      setFill(getColor(isColor)); }
    else if (shapeType.equals(RECTANGLE_TYPE)) {
      setRectangleSize();
      setFill(getColor(isColor)); }
    else {
      System.out.println("Error unimplemented shape type string");
      setSquareSize();
      setFill(getColor(isColor)); }
  }

  private void setTurtleViewGray() {
    isActive = false;
    if (isIMG) {
      if (!extension.contains("_gray")) {
        setShapeSize();
        String gray_image_filepath =
                image_filepath.substring(0, image_filepath.lastIndexOf(".")) + "_gray" + extension;
        setImage(gray_image_filepath);
        extension = "_gray" + extension; } }
    else {
      updateShapeGrey(); }
  }

  private void setTurtleViewColor() {
    isActive = true;
    if (isIMG) {
      if (extension.contains("_gray")) {
        setShapeSize();
        String filepath =
                image_filepath.substring(0, image_filepath.lastIndexOf("_")) + image_filepath
                        .substring(image_filepath.lastIndexOf("."));
        setImage(filepath);
        extension = filepath.substring(image_filepath.lastIndexOf(".")); } }
    else {
      updateShapeColor(); }
  }

  private Color getColor(boolean isColor) {
    if (isColor) {
      return COLOR; }
    return GREY;
  }

  private void updateShapeGrey() {
    setFill(getColor(false));
  }

  private void updateShapeColor() {
    setFill(getColor(true));
  }

  private void setShapeSize() {
    setWidth(shapeWidth);
    setHeight(shapeHeight);
  }

  private void setSquareSize() {
    setWidth(shapeWidth);
    setHeight(shapeWidth);
  }

  private void setRectangleSize() {
    setWidth(shapeWidth);
    setHeight(shapeWidth * RECTANGLE_RATIO);
  }

  private List<Map.Entry<String, String>> loadDict(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    List<Map.Entry<String, String>> dict = new ArrayList<>();
    for (String tabType : Collections.list(resources.getKeys())) {
      String tabName = resources.getString(tabType);
      dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName)); }
    return dict;
  }
}

