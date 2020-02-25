package slogo.turtle;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;

    public TurtleHabitat(double width, double height){
        turtle = new TurtleView(50, 25);
        turtle.setFill(turtle.getImage());
        myTurtleHabitat = new Pane(turtle);
        changeSize(width, height);
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

    public Rectangle updateHabitat(Color penColor){
        Rectangle r = new Rectangle(1,1);
        r.setX(turtle.getX());
        System.out.println(turtle.getX());
        r.setY(turtle.getY());
        r.setFill(penColor);
        return r;
    }
}
