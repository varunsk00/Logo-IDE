package slogo.turtle;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;

public class TurtleHabitat {
    private Pane myTurtleHabitat;
    private TurtleView turtle;

    public TurtleHabitat(){
        turtle = new TurtleView(50, 25);
        turtle.setFill(turtle.getImage());
        myTurtleHabitat = new Pane(turtle);
    }

    public Pane getTurtleHabitat(){
        return myTurtleHabitat;
    }

    public TurtleView getTurtle(){
        return turtle;
    }

    public void updateTurtleHabitat(Turtle turtle){
        myTurtleHabitat.getChildren().clear();
        Rectangle r = new Rectangle();
        myTurtleHabitat.getChildren().add(r);
    }
}
