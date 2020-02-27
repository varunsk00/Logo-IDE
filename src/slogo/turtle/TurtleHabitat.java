package slogo.turtle;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.List;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;
    private List<Polyline> myLines;
    private static double DEFAULT_TURTLE_WIDTH = 50.0;
    private static double DEFAULT_TURTLE_HEIGHT = 25.0;

    private double lastx;
    private double lasty;

    public TurtleHabitat(double width, double height){
        turtle = initializeTurtleView(width, height);
        myTurtleHabitat = new Pane(turtle);
        myLines = new ArrayList<>();
        changeSize(width, height);
        lastx = turtle.getX() + turtle.getWidth()/2;
        lasty = turtle.getY() + turtle.getHeight()/2;
    }

    private TurtleView initializeTurtleView(double habitatWidth, double habitatHeight){
        TurtleView tempTurtle = new TurtleView(DEFAULT_TURTLE_WIDTH, DEFAULT_TURTLE_HEIGHT,
                                                habitatWidth, habitatHeight);
        tempTurtle.setFill(tempTurtle.getImage());
        tempTurtle.setX(tempTurtle.getXOffset());
        tempTurtle.setY(tempTurtle.getYOffset());
        return tempTurtle;
    }

    private void changeSize(double width, double height){
        myTurtleHabitat.setPrefWidth(width);
        myTurtleHabitat.setPrefHeight(height);
    }

    public Pane getTurtleHabitat(){
        return myTurtleHabitat;
    }

    public TurtleView getTurtle(){
        return turtle;
    }

    public void setBackground(Color c){
        myTurtleHabitat.setBackground(new Background(new BackgroundFill(c, CornerRadii.EMPTY, Insets.EMPTY)));
    }

    public void penDraw(Color penColor, Point loc){
        double x_coor = loc.getX();
        double y_coor = loc.getY();
        Polyline pen  = new Polyline();
        myLines.add(pen);
        myTurtleHabitat.getChildren().add(pen);

        double xOffsetCoord = x_coor + turtle.getXOffset() + turtle.getWidth()/2;
        double yOffsetCoord = y_coor + turtle.getYOffset() + turtle.getHeight()/2;
        Double[] points = new Double[] {lastx, lasty, xOffsetCoord, yOffsetCoord};
        lastx = xOffsetCoord;
        lasty = yOffsetCoord;

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
