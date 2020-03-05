package slogo.turtle;
import javafx.beans.Observable;
import javafx.collections.ObservableMap;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Polyline;
import javafx.scene.control.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.awt.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;
    private List<Polyline> myLines;
    private static double DEFAULT_TURTLE_WIDTH = 50.0;
    private static double DEFAULT_TURTLE_HEIGHT = 25.0;
    private Map<Integer, TurtleView> allTurtles;
    private Map<Integer, Double> lastx;
    private Map<Integer, Double> lasty;

    private double habitatWidth;
    private double habitatHeight;
    private Button viewTurtles;
    Rectangle rec;

    public TurtleHabitat(double width, double height){
        allTurtles = new HashMap<Integer, TurtleView>();
        lastx = new HashMap<Integer, Double>();
        lasty = new HashMap<Integer, Double>();
        myTurtleHabitat = new Pane();
        habitatWidth = width;
        habitatHeight = height;
        myLines = new ArrayList<>();
        changeSize(width, height);

        viewTurtles = createViewButton();
        myTurtleHabitat.getChildren().add(viewTurtles);
    }

    private Button createViewButton (){
        Button button = new Button("View Turtles");
        button.setOnAction(event -> viewTurtleInformation());
        return button;
    }

    private void viewTurtleInformation(){
        Stage s = new Stage();
        Pane root = new Pane();
        Scene sc = new Scene(root, 400, 400);
        ListView<Button> turtleView = new ListView<>();
        for (int turtleID: allTurtles.keySet()){
            Button button = new Button("Turtle " + turtleID);
            button.setOnAction(event -> displayInformation(turtleID, root));
            turtleView.getItems().addAll(button);
        }
        turtleView.setPrefSize(100, turtleView.getItems().size()*30);
        root.getChildren().addAll(turtleView);
        s.setScene(sc);
        s.show();
    }

    private void displayInformation(int id, Pane p){
        p.getChildren().remove(rec);
        rec = new Rectangle(DEFAULT_TURTLE_WIDTH,DEFAULT_TURTLE_HEIGHT);
        rec.setLayoutX(200);
        rec.setLayoutY(50);
        rec.setFill(allTurtles.get(id).getFill());
        p.getChildren().add(rec);
    }

    public void updateHabitat(int id, Turtle turtle){
        TurtleView tempTurtle = new TurtleView(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT,
                habitatWidth, habitatHeight);
        tempTurtle.setFill(tempTurtle.getImage());
        tempTurtle.setX(tempTurtle.getXOffset());
        tempTurtle.setY(tempTurtle.getYOffset());
        if (!allTurtles.containsKey(id)){
            allTurtles.putIfAbsent(id, tempTurtle);
            lastx.putIfAbsent(id, tempTurtle.getX() + tempTurtle.getWidth()/2);
            lasty.putIfAbsent(id, tempTurtle.getY() + tempTurtle.getHeight()/2);
            myTurtleHabitat.getChildren().addAll(tempTurtle);
        }
        allTurtles.get(id).updateTurtleView(turtle);
    }

    private void changeSize(double width, double height){
        myTurtleHabitat.setPrefWidth(width);
        myTurtleHabitat.setPrefHeight(height);
    }

    public Pane getTurtleHabitat(){
        return myTurtleHabitat;
    }

    public TurtleView getTurtle(int turtleID){
        return allTurtles.get(turtleID);
    }

    public void setBackground(Color c){
        myTurtleHabitat.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void penDraw(Color penColor, Point loc, int turtleID){
        TurtleView turtle = allTurtles.get(turtleID);
        double x_coor = loc.getX();
        double y_coor = loc.getY();
        Polyline pen  = new Polyline();
        myLines.add(pen);
        myTurtleHabitat.getChildren().add(pen);

        double xOffsetCoord = x_coor + turtle.getXOffset() + turtle.getWidth()/2;
        double yOffsetCoord = y_coor + turtle.getYOffset() + turtle.getHeight()/2;
        Double[] points = new Double[] {lastx.get(turtleID), lasty.get(turtleID), xOffsetCoord, yOffsetCoord};
        lastx.put(turtleID, xOffsetCoord);
        lasty.put(turtleID, yOffsetCoord);

        if (loc.getDrawn()) {
            pen.getPoints().addAll(points);
        }
        pen.setStroke(penColor);
        //p.getStrokeDashArray().addAll(2d, 21d);
        pen.setStrokeWidth(2.0);
        if(turtle.isCleared()){
            for (Polyline p : myLines) {
                p.getPoints().clear();
                myTurtleHabitat.getChildren().remove(p);
            }
            myLines.clear();
        }
    }
}
