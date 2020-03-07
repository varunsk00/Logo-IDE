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
    private double penWidth;
    private boolean isActive;
    private boolean prevActive;

    public TurtleView(double width, double height, double habitatWidth, double habitatHeight){
        super(width, height);
        this.xOffset = habitatWidth/2 - getWidth()/2;
        this.yOffset = habitatHeight/2 - getHeight();
        this.img = new Image(image_filepath);
        penWidth = 2.0;
        isActive = true;
        prevActive = true;
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
        penWidth = turtle.getPenSize();
        cleared = turtle.isCleared();
        if(prevActive!= isActive){
            turtle.setActive(isActive);
        }
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
        prevActive = isActive;
    }

    public Color getPenColor(){return penColor;}

    public double getPenWidth(){return penWidth;}

    public void setPenWidth(double width){penWidth = width;}

    public void setPenColor(Color inputColor){penColor = inputColor;}

    public void setActive(boolean b){isActive = b;}

    public boolean getActive(){return isActive;}

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
        isActive = false;
        if(!extension.contains("_gray")){
            String gray_image_filepath = image_filepath.substring(0, image_filepath.lastIndexOf(".")) + "_gray" + extension;
            setImage(gray_image_filepath);
            extension = "_gray" + extension;
        }
    }

    private void setTurtleViewColor(){
        isActive = true;
        if(extension.contains("_gray")){
            String filepath = image_filepath.substring(0, image_filepath.lastIndexOf("_")) + image_filepath.substring(image_filepath.lastIndexOf("."));
            setImage(filepath);
            extension = filepath.substring(image_filepath.lastIndexOf("."));
        }
    }
}

