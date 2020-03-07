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

public class TurtleView extends Rectangle {

  final private String SHAPEMAP_RESOURCE = "slogo.resources.shape.shape_map";
  final private double RECTANGLE_RATIO = 1.426;
  final private Color COLOR = Color.GREEN;
  final private Color GREY = Color.GREY;
  private double xOffset;
  private double yOffset;
  private String image_filepath = "slogo/resources/images/turtle_green.png";
  private String extension = image_filepath.substring(image_filepath.indexOf("."));
  private boolean isIMG;
  private Image img;
  private double shapeWidth;
  private double shapeHeight;
  private boolean cleared;
  private Color penColor = Color.BLACK;
  private String CIRCLE_TYPE = "CIRCLE";
  private String RECTANGLE_TYPE = "RECT"; // 1.0 width & 1.5 width
  private String DEFAULT_TYPE = "CIRCLE";
  private String SQUARE_TYPE = "SQUARE"; // 1.0 width & 1.0 height
  private double penWidth;

  public TurtleView(double width, double height, double habitatWidth, double habitatHeight) {
    super(width, height);
    shapeWidth = width;
    shapeHeight = height;
    this.xOffset = habitatWidth / 2 - getWidth() / 2;
    this.yOffset = habitatHeight / 2 - getHeight() / 2;
    isIMG = true;
    this.img = new Image(image_filepath);
    penWidth = 2.0;
  }

  public boolean isIMG() {
    return isIMG;
  }

  public double getShapeWidth() {
    return shapeWidth;
  }

  public double getShapeHeight() {
    return shapeHeight;
  }

  public ImagePattern getImage() {
    return new ImagePattern(img);
  }

  public void setImage(String filepath) {
    isIMG = true;
    image_filepath = filepath;
    setFill(new ImagePattern(new Image(filepath)));
  }

  public void setShape(int shapeID, boolean isColor) {

    isIMG = false;
    List<Map.Entry<String, String>> shapeDict = loadDict(SHAPEMAP_RESOURCE);

    for (Map.Entry<String, String> entry : shapeDict) {
      if (String.valueOf(shapeID).equals(entry.getKey())) {
        setShapeFactory(entry.getValue(), isColor);
        return;
      }
    }

    setShapeFactory(DEFAULT_TYPE, isColor);
    System.out.println("Error: Unimplemented shape type");
  }

  private void setShapeFactory(String shapeType, boolean isColor) {
    if (shapeType.equals(SQUARE_TYPE)) {
      setSquareSize();
      System.out.println(getColor(isColor));
      setFill(getColor(isColor));
    } else if (shapeType.equals(RECTANGLE_TYPE)) {
      setRectangleSize();
      setFill(getColor(isColor));
    } else {
      System.out.println("Error unimplemented shape type string");
      setSquareSize();
      setFill(getColor(isColor));
    }
  }

  private Color getColor(boolean isColor) {
      if (isColor) {
          return COLOR;
      }
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

  public void updateTurtleView(Turtle turtle) {
    setRotate(turtle.getHeading());
    setX(turtle.getXLocation() + xOffset);
    setY(turtle.getYLocation() + yOffset);
    setVisible(turtle.isShowTurtle());
    penWidth = turtle.getPenSize();
    cleared = turtle.isCleared();
    if (cleared) {
      turtle.setCleared(false);
      turtle.handleClear();
    }
    if (!turtle.getIsActive()) {
      setTurtleViewGray();
    }
    if (turtle.getIsActive()) {
      setTurtleViewColor();
    }
  }

  public Color getPenColor() {
    return penColor;
  }

  public void setPenColor(Color inputColor) {
    penColor = inputColor;
  }

  public double getPenWidth() {
    return penWidth;
  }

  public void setPenWidth(double width) {
    penWidth = width;
  }


  public double getXOffset() {
    return xOffset;
  }

  public double getYOffset() {
    return yOffset;
  }

  public boolean isCleared() {
    return cleared;
  }

  private void setTurtleViewGray() {
    if (isIMG) {
      if (!extension.contains("_gray")) {
        setShapeSize();
        String gray_image_filepath =
            image_filepath.substring(0, image_filepath.lastIndexOf(".")) + "_gray" + extension;
        setImage(gray_image_filepath);
        extension = "_gray" + extension;
      }
    } else {
      updateShapeGrey();
    }
  }

  private void setTurtleViewColor() {
    if (isIMG) {
      if (extension.contains("_gray")) {
        setShapeSize();
        String filepath =
            image_filepath.substring(0, image_filepath.lastIndexOf("_")) + image_filepath
                .substring(image_filepath.lastIndexOf("."));
        setImage(filepath);
        extension = filepath.substring(image_filepath.lastIndexOf("."));
      }
    } else {
      updateShapeColor();
    }
  }

  private List<Map.Entry<String, String>> loadDict(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    List<Map.Entry<String, String>> dict = new ArrayList<>();
    for (String tabType : Collections.list(resources.getKeys())) {
      String tabName = resources.getString(tabType);
      dict.add(new AbstractMap.SimpleEntry<>(tabType, tabName));
    }
    return dict;
  }
}

