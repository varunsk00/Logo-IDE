package slogo.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Stack;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

/**
 * TurtleHabitat.java
 *
 * A JavaFX Pane upon which multiple TurtleView objects can be modified and a Pen can be drawn and modified.
 *
 * @author Varun Kosgi
 * @author Alexander Uzochukwu
 */
public class TurtleHabitat extends Pane {

  private final int TEXT_X_LOCATION = 200;
  private final int TEXT_Y_LOCATION = 100;
  private final int COLORBOXSIZE = 10;
  private final int COLORBOX_X_LOCATION = 260;
  private final int COLORBOX_Y_LOCATION = 140;
  private final int INFO_PANE_SIZE = 400;
  private final int TURTLE_LIST_WIDTH = 100;
  private final int BUTTON_HEIGHT = 30;
  private final int PICTURE_X_LOCATION = 200;
  private final int PICTURE_Y_LOCATION = 50;
  private final double DEFAULT_TURTLE_WIDTH = 50.0;
  private final double DEFAULT_TURTLE_HEIGHT = 25.0;
  private double habitatWidth;
  private double habitatHeight;
  private Color backgroundColor;
  private Rectangle rec;
  private Rectangle clrBox;
  private Text information;
  private Stack<List<Polyline>> polylineStack = new Stack<>();
  private List<Polyline> myLines;
  private Map<Integer, TurtleView> allTurtleViews;
  private Map<Integer, Turtle> allTurtles;
  private Map<Integer, Double> lastx;
  private Map<Integer, Double> lasty;

  /**
   * Constructor to initialize Turtle->ID map, TurtleView->ID map, previous coordinate map, pen locations, and
   * habitat size; automatically scales the Habitat to the user's window size.
   *
   * @param width the width of the habitat
   * @param height the height of the habitat
   */
  public TurtleHabitat(double width, double height) {
    allTurtleViews = new HashMap<>();
    allTurtles = new HashMap<>();
    lastx = new HashMap<>();
    lasty = new HashMap<>();
    habitatWidth = width;
    habitatHeight = height;
    myLines = new ArrayList<>();
    changeSize(width, height);
  }

  /**
   * Updates each individual TurtleView object in the TurtleHabitat
   *
   * @param ids the list of all Turtle IDs in the TurtleHabitat
   * @param turtles the list of all Turtle objects in the TurtleHabitat
   * @param penColors the list of each Turtle's respective pen color
   */
  public void updateHabitat(List<Integer> ids, List<Turtle> turtles, List<Color> penColors) {
    for (int i = 0; i < ids.size(); i++) {
      updateSingleTurtle(ids.get(i), turtles.get(i), penColors.get(i)); }
    for (Entry<Integer, TurtleView> e: new HashSet<>(allTurtleViews.entrySet())) {
      if (!e.getValue().isUpdated()) {
        getChildren().remove(e.getValue());
        allTurtles.remove(e.getKey(), allTurtles.get(e.getKey()));
        allTurtleViews.remove(e.getKey(), e.getValue()); }
      e.getValue().setUpdated(false); }
  }

  /**
   * Launches window to show all current Turtles in the habitat
   */
  public void viewTurtleInformation() {
    Stage s = new Stage();
    Pane root = new Pane();
    Scene sc = new Scene(root, INFO_PANE_SIZE, INFO_PANE_SIZE);
    ListView<Button> turtleView = new ListView<>();
    for (int turtleID : allTurtleViews.keySet()) {
      Button button = new Button("Turtle " + turtleID);
      button.setOnAction(event -> displayInformation(turtleID, root));
      turtleView.getItems().addAll(button);
    }
    turtleView.setPrefSize(TURTLE_LIST_WIDTH, turtleView.getItems().size() * BUTTON_HEIGHT);
    root.getChildren().addAll(turtleView);
    s.setScene(sc);
    s.show();
  }

  /**
   * Utilizes a Stack to remove the previously drawn Pen (Polyline)
   * Called by Ctrl+Z keybind within TerminalController.java
   */
  public void undoPen() {
    if (!polylineStack.isEmpty()) {
      List<Polyline> toRemove = polylineStack.pop();
      for (Polyline p : myLines) {
        p.getPoints().clear();
        getChildren().remove(p); }
      myLines.clear();
      myLines = toRemove;
      for (Polyline p : myLines) {
        getChildren().add(p); } }
  }

  /**
   * Updates the current image file used for each TurtleView in the TurtleHabitat
   * @param filepath the location of the Image File
   */
  public void updateAllTurtlesImage(String filepath) {
    for (TurtleView turtleView : getAllTurtleViews()) {
      turtleView.setImage(filepath); }
  }

  /**
   * Updates the current Shape used for each TurtleView in the TurtleHabitat
   * @param colorID the ID number assigned to the current color by the user
   */
  public void updateAllTurtlesShapeColor(int colorID) {
    for (TurtleView turtleView : getAllTurtleViews()) {
      turtleView.setShape(colorID, true); }
  }

  /**
   * @param turtleID a chosen Turtle's ID
   * @return the Turtle associated with that ID
   */
  public Turtle getTurtle(int turtleID) {
    return allTurtles.get(turtleID);
  }

  /**
   * @param turtleID a chosen Turtle's ID
   * @return the TurtleView associated with that ID
   */
  public TurtleView getTurtleView(int turtleID) {
    return allTurtleViews.get(turtleID);
  }

  /**
   * @return the Map of IDs -> TurtleViews
   */
  public Map<Integer, TurtleView> getExistingTurtleViews() {
    return allTurtleViews;
  }

  /**
   * Updates the Pen Color associated with each TurtleView
   * @param newPenColor the color to update
   */
  public void setAllTurtlesPenColor(Color newPenColor) {
    for (TurtleView turtle : new ArrayList<>(allTurtleViews.values())) {
      turtle.setPenColor(newPenColor); }
  }

  /**
   * Sets the TurtleHabitat background color
   * @param c new color
   */
  public void setBackground(Color c) {
    setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
    backgroundColor = c;
  }

  /**
   * @return current color of the background
   */
  public Color getBackgroundColor() {
    return backgroundColor;
  }

  /**
   * Saves the previously drawn line to a Stack after each execution of a command
   */
  public void saveToStack() {
    List<Polyline> temp = new ArrayList<>();
    for (Polyline p : myLines) {
      Polyline tempP = new Polyline();
      tempP.getPoints().addAll(p.getPoints());
      temp.add(tempP); }
    polylineStack.add(temp);
  }


  private void displayInformation(int id, Pane p) {
    p.getChildren().removeAll(rec, information, clrBox);
    rec = new Rectangle(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT);
    clrBox = new Rectangle(COLORBOXSIZE, COLORBOXSIZE);
    rec.setLayoutX(PICTURE_X_LOCATION);
    rec.setLayoutY(PICTURE_Y_LOCATION);
    clrBox.setLayoutX(COLORBOX_X_LOCATION);
    clrBox.setLayoutY(COLORBOX_Y_LOCATION);
    rec.setFill(allTurtleViews.get(id).getFill());
    clrBox.setFill(allTurtleViews.get(id).getPenColor());
    double xLoc = allTurtleViews.get(id).getX() - allTurtleViews.get(id).getXOffset();
    double yLoc = allTurtleViews.get(id).getY() - allTurtleViews.get(id).getYOffset();
    information = new Text(TEXT_X_LOCATION, TEXT_Y_LOCATION,
        "Position: (" + xLoc + " , " + yLoc + ")\n" +
            "Heading: " + allTurtleViews.get(id).getRotate() + "\n" +
            "PenDown: " + allTurtles.get(id).isPenDown() + "\n" +
            "PenColor: ");
    p.getChildren().addAll(rec, information, clrBox);
  }

  private void updateSingleTurtle(int id, Turtle turtle, Color c) {
    TurtleView tempTurtle = new TurtleView(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT,
        habitatWidth, habitatHeight);
    tempTurtle.setFill(tempTurtle.getImage());
    tempTurtle.setX(tempTurtle.getXOffset());
    tempTurtle.setY(tempTurtle.getYOffset());
    if (!allTurtleViews.containsKey(id)) {
      allTurtleViews.putIfAbsent(id, tempTurtle);
      lastx.putIfAbsent(id, tempTurtle.getLayoutX() + tempTurtle.getShapeWidth() / 2);
      lasty.putIfAbsent(id, tempTurtle.getLayoutY() + tempTurtle.getShapeHeight() / 2);
      getChildren().addAll(tempTurtle); }
    allTurtles.put(id, turtle);
    allTurtleViews.get(id).updateTurtleView(turtle);
    allTurtleViews.get(id).setUpdated(true);
    allTurtleViews.get(id).setPenColor(c);
    if (turtle.isPenDown()) {
      for (Point loc : turtle.locationsList()) {
        penDraw(loc,id); } }
  }

  private void changeSize(double width, double height) {
    setPrefWidth(width);
    setPrefHeight(height);
  }

  private ArrayList<TurtleView> getAllTurtleViews(){
    return new ArrayList<>(allTurtleViews.values());
  }

  private void penDraw(Point loc, int turtleID) { //converts Turtle coordinates [center=(0,0)] to Scene coordinates in JavaFX
    TurtleView turtle = allTurtleViews.get(turtleID);
    Color penColor = turtle.getPenColor();
    double x_coor = loc.getX();
    double y_coor = loc.getY();
    Polyline pen = new Polyline();
    myLines.add(pen);
    getChildren().add(pen);
    double xOffsetCoord = x_coor + turtle.getXOffset() + turtle.getWidth() / 2;
    double yOffsetCoord = y_coor + turtle.getYOffset() + turtle.getHeight() / 2;
    Double[] points = new Double[]{lastx.get(turtleID), lasty.get(turtleID), xOffsetCoord,
        yOffsetCoord};
    lastx.put(turtleID, xOffsetCoord);
    lasty.put(turtleID, yOffsetCoord);

    if (loc.getDrawn()) {
      pen.getPoints().addAll(points); }
    pen.setStroke(penColor);
    pen.setStrokeWidth(turtle.getPenWidth());
    if (turtle.isCleared()) {
      for (Polyline p : myLines) {
        p.getPoints().clear();
        getChildren().remove(p); }
      myLines.clear(); }
  }
}
