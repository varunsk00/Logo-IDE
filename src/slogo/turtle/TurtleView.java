package slogo.turtle;

import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.scene.paint.ImagePattern;
import javafx.scene.shape.Rectangle;

public class TurtleView extends Rectangle {
    private double xOffset;
    private double yOffset;
    private String image_filepath = "slogo/resources/images/turtle_green.png";
    private String extension = image_filepath.substring(image_filepath.indexOf("."));
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
        setFill(new ImagePattern(new Image(filepath)));
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
        if(!turtle.getIsActive()){
            setTurtleViewGray();
        }
        if(turtle.getIsActive()){
            setTurtleViewColor();
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

    private void setTurtleViewGray(){
        if(!extension.contains("_gray")){
            String gray_image_filepath = image_filepath.substring(0, image_filepath.lastIndexOf(".")) + "_gray" + extension;
            setImage(gray_image_filepath);
            extension = "_gray" + extension;
        }
    }

    private void setTurtleViewColor(){
        if(extension.contains("_gray")){
            String filepath = image_filepath.substring(0, image_filepath.lastIndexOf("_")) + image_filepath.substring(image_filepath.lastIndexOf("."));
            setImage(filepath);
            extension = filepath.substring(image_filepath.lastIndexOf("."));
        }
    }
}

