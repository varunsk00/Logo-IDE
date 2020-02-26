package slogo.turtle;
import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;
    private Polyline pen  = new Polyline();

    public TurtleHabitat(double width, double height){
        turtle = new TurtleView(50, 25);
        turtle.setFill(turtle.getImage());
        turtle.setX(width/2);
        turtle.setY(height/2);
        myTurtleHabitat = new Pane(turtle);
        changeSize(width, height);
        myTurtleHabitat.getChildren().add(pen);
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
        Double[] points = new Double[] {turtle.centerX(), turtle.centerY(),
                                        x_coor + turtle.getXOffset() + 50/(2), y_coor + turtle.getYOffset() + 25/(2)};
        pen.getPoints().addAll(points);
        //p.getStrokeDashArray().addAll(2d, 21d);
        pen.setStroke(penColor);
        pen.setStrokeWidth(2.0);
        if(turtle.isCleared()){
            myTurtleHabitat.getChildren().remove(pen);
        }
    }
}
