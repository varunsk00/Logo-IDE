package slogo.turtle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class TurtleView extends Rectangle {
    private double habHeight;
    private double habWidth;
    private double xOffset;
    private double yOffset;
    private String image_filepath = "slogo/resources/images/turtle_green.png";
    private Image img;
    private boolean cleared;
    public TurtleView(double width, double height, double habitatHeight, double habitatWidth){
        super(width, height);
        this.habHeight = habitatHeight;
        this.habWidth = habitatWidth;
        System.out.println(habitatHeight);
        this.xOffset = habitatWidth/2 + getWidth();
        this.yOffset = habitatHeight/2 - habitatHeight/8;
        this.img = new Image(image_filepath);
    }

    public ImagePattern getImage(){
        return new ImagePattern(img);
    }

    public void setImage(String filepath){
        image_filepath = filepath;
    }

    public void updateTurtleView(Turtle turtle) {
        setRotate(turtle.getHeading());
        setX(turtle.getXLocation() + xOffset);
        setY(turtle.getYLocation() + yOffset);
        setVisible(turtle.isShowTurtle());
        cleared = turtle.isCleared();
        if (cleared) {
            turtle.setCleared(false);
            turtle.handleClear();
        }
    }

    public double getXOffset(){
        return xOffset;
    }

    public double getYOffset(){
        return yOffset;
    }

    public boolean isCleared(){
        return cleared;
    }
}

