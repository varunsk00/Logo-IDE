package slogo.turtle;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;
import javafx.scene.image.Image;
import javafx.scene.shape.Shape;

public class TurtleView extends Rectangle {
    private String image_filepath = "slogo/resources/images/turtle_green.png";
    private Image img;
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
}

