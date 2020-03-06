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
    private Color penColor = Color.BLACK;

    public TurtleView(double width, double height, double habitatWidth, double habitatHeight){
        super(width, height);
        this.xOffset = habitatWidth/2 - getWidth()/2;
        this.yOffset = habitatHeight/2 - getHeight();
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

    public Color getPenColor(){return penColor;}

    public void setPenColor(Color inputColor){penColor = inputColor;}

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

