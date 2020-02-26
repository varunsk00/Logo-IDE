package slogo.turtle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class TurtleView extends Rectangle {
    private static final double X_OFFSET = 320;
    private static final double Y_OFFSET = 360;
    private String image_filepath = "slogo/resources/images/turtle_green.png";
    private Image img;
    private boolean cleared;
    public TurtleView(int width, int height){
        super(width, height);
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
        setX(turtle.getXLocation() + X_OFFSET);
        setY(turtle.getYLocation() + Y_OFFSET);
        setVisible(turtle.isShowTurtle());
        cleared = turtle.isCleared();
        if (cleared) {
            turtle.setCleared(false);
            turtle.handleClear();
        }
    }

    public double centerX(){
        return getX() + getWidth()/2;
    }

    public double centerY(){
        return getY() + getHeight()/2;
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

