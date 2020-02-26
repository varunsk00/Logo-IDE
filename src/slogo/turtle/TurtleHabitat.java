package slogo.turtle;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.util.ArrayList;
import java.util.List;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;
    private List<Polyline> myLines;

    private double lastx;
    private double lasty;

    public TurtleHabitat(double width, double height){
        turtle = new TurtleView(50, 25);
        turtle.setFill(turtle.getImage());
        turtle.setX(width/2);
        turtle.setY(height/2);
        myTurtleHabitat = new Pane(turtle);
        myLines = new ArrayList<>();
        changeSize(width, height);
        lastx = turtle.centerX();
        lasty = turtle.centerY();
    }

    public void changeSize(double width, double height){
        myTurtleHabitat.setPrefSize(width, height);
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

    public void penDraw(Color penColor, double x_coor, double y_coor){
        Polyline pen  = new Polyline();
        myLines.add(pen);
        myTurtleHabitat.getChildren().add(pen);

        double xOffsetCoord = x_coor + turtle.getXOffset() + 50/(2);
        double yOffsetCoord = y_coor + turtle.getYOffset() + 25/(2);
        Double[] points = new Double[] {lastx, lasty, xOffsetCoord, yOffsetCoord};
        lastx = xOffsetCoord;
        lasty = yOffsetCoord;

        pen.getPoints().addAll(points);
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
