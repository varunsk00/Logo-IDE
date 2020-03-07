package slogo.turtle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
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


public class TurtleHabitat extends Pane {

  private static final int INFO_PANE_SIZE = 400;
  private static final int TURTLE_LIST_WIDTH = 100;
  private static final int BUTTON_HEIGHT = 30;
  private static final int PICTURE_X_LOCATION = 200;
  private static final int PICTURE_Y_LOCATION = 50;
  private static final double PEN_WIDTH = 2.0;
  private static double DEFAULT_TURTLE_WIDTH = 50.0;
  private static double DEFAULT_TURTLE_HEIGHT = 25.0;
  private static Color backgroundColor;
  Rectangle rec;
  Rectangle clrBox;
  Text information;
  private Stack<List<Polyline>> polylineStack = new Stack<>();
  private List<Polyline> myLines;
  private Map<Integer, TurtleView> allTurtleViews;
  private Map<Integer, Turtle> allTurtles;
  private Map<Integer, Double> lastx;
  private Map<Integer, Double> lasty;
  private double habitatWidth;
  private double habitatHeight;

  public TurtleHabitat(double width, double height) {
    allTurtleViews = new HashMap<Integer, TurtleView>();
    allTurtles = new HashMap<Integer, Turtle>();
    lastx = new HashMap<Integer, Double>();
    lasty = new HashMap<Integer, Double>();
    habitatWidth = width;
    habitatHeight = height;
    myLines = new ArrayList<>();
    changeSize(width, height);
  }

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

  private void displayInformation(int id, Pane p) {
    p.getChildren().removeAll(rec, information, clrBox);
    rec = new Rectangle(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT);
    rec.setX(PICTURE_X_LOCATION);
    rec.setY(PICTURE_Y_LOCATION);
    rec.setFill(allTurtleViews.get(id).getFill());
    information = new Text(200, 100,
        "Position: (" + allTurtles.get(id).getXLocation() + " , " + allTurtles.get(id)
            .getYLocation() + ")\n" +
            "Heading: " + allTurtles.get(id).getHeading() + "\n" +
            "PenDown: " + allTurtles.get(id).isPenDown() + "\n" +
            "PenColor: ");
    clrBox = new Rectangle(10, 10);
    clrBox.setLayoutX(260);
    clrBox.setLayoutY(140);
    clrBox.setFill(allTurtleViews.get(id).getPenColor());
    p.getChildren().addAll(rec, information, clrBox);
  }

  public void updateHabitat(int id, Turtle turtle) {
    TurtleView tempTurtle = new TurtleView(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT,
        habitatWidth, habitatHeight);
    //System.out.println(habitatHeight);
    //
    tempTurtle.setFill(tempTurtle.getImage());
    //System.out.println("retertre");
    //System.out.println(tempTurtle.isIMG());
    tempTurtle.setX(tempTurtle.getXOffset());
    tempTurtle.setY(tempTurtle.getYOffset());
    if (!allTurtleViews.containsKey(id)) {
      allTurtleViews.putIfAbsent(id, tempTurtle);
      lastx.putIfAbsent(id, tempTurtle.getLayoutX() + tempTurtle.getShapeWidth() / 2);
      lasty.putIfAbsent(id, tempTurtle.getLayoutY() + tempTurtle.getShapeHeight() / 2);
      getChildren().addAll(tempTurtle);
    }
    allTurtles.put(id, turtle);
    allTurtleViews.get(id).updateTurtleView(turtle);
  }

  private void changeSize(double width, double height) {
    setPrefWidth(width);
    setPrefHeight(height);
  }

  public TurtleView getTurtle(int turtleID) {
    return allTurtleViews.get(turtleID);
  }

  public List<TurtleView> getAllTurtleViews() {
    return new ArrayList<>(allTurtleViews.values());
  }

  public void setAllTurtlesPenColor(Color newPenColor) {
    for (TurtleView turtle : new ArrayList<>(allTurtleViews.values())) {
      turtle.setPenColor(newPenColor);
    }
  }

  public void updateAllTurtlesImage(String filepath) {
    for (TurtleView turtleView : getAllTurtleViews()) {
      turtleView.setImage(filepath);
    }
  }

  public void updateAllTurtlesShapeColor(int colorID) {
    for (TurtleView turtleView : getAllTurtleViews()) {
      turtleView.setShape(colorID, true);
    }
  }

  public void setBackground(Color c) {
    setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
    backgroundColor = c;
  }

  public Color getBackgroundColor() {
    return backgroundColor;
  }

  public void undoPen() {
    if (!polylineStack.isEmpty()) {
      List<Polyline> toRemove = polylineStack.pop();
      for (Polyline p : myLines) {
        p.getPoints().clear();
        getChildren().remove(p);
      }
      myLines.clear();
      myLines = toRemove;
      for (Polyline p : myLines) {
        getChildren().add(p);
      }
    }
  }
// add current list to a stack

  public void saveToStack() {
    List<Polyline> temp = new ArrayList<>();
    for (Polyline p : myLines) {
      Polyline tempP = new Polyline();
      tempP.getPoints().addAll(p.getPoints());
      temp.add(tempP);
    }
    polylineStack.add(temp);
  }

  public void penDraw(Color penColor, Point loc, int turtleID) {
    TurtleView turtle = allTurtleViews.get(turtleID);
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
      pen.getPoints().addAll(points);
    }
    pen.setStroke(penColor);
    //p.getStrokeDashArray().addAll(2d, 21d);
    pen.setStrokeWidth(PEN_WIDTH);
    if (turtle.isCleared()) {
      for (Polyline p : myLines) {
        p.getPoints().clear();
        getChildren().remove(p);
      }
      myLines.clear();
    }
  }
}
