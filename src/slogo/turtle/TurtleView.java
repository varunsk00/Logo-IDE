package slogo.turtle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class TurtleView extends Rectangle {
    private static double turtleViewWidth;
    private static double turtleViewHeight;
    private static final int HEADER_HEIGHT = 45;
    private static final double X_OFFSET = 320 - turtleViewWidth/2;
    private static final double Y_OFFSET = 360 - turtleViewHeight/2 - HEADER_HEIGHT;
    private String image_filepath = "slogo/resources/images/turtle_green.png";
    private Image img;
    private boolean cleared;
    public TurtleView(double width, double height){
        super(width, height);
        turtleViewWidth = width;
        turtleViewHeight = height;
        img = new Image(image_filepath);
    }

    public ImagePattern getImage(){
        return new ImagePattern(img);
    }

    public void setImage(String filepath){
        image_filepath = filepath;
    }

    //FIXME: CENTER RECTANGLE BECAUSE COORDINATES ARE IN THE TOP LEFT
    public void updateTurtleView(Turtle turtle) {
        setRotate(turtle.getHeading());
        setX(turtle.getXLocation() + X_OFFSET - getWidth()/2);
        setY(turtle.getYLocation() + Y_OFFSET - getHeight()/2);
        setVisible(turtle.isShowTurtle());
        cleared = turtle.isCleared();
        if (cleared) {
            turtle.setCleared(false);
            turtle.handleClear();
        }
    }

    public double getXOffset(){
        return X_OFFSET;
    }

    public double getYOffset(){
        return Y_OFFSET;
    }

    public boolean isCleared(){
        return cleared;
    }
}

