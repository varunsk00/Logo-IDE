package slogo.controller;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import slogo.workspace.Workspace;

import java.util.ResourceBundle;

public class SliderController {

    private ResourceBundle myResources;

    private Slider zoom;
    private Slider imagesize;
    private Slider penWidth;

    private static final int MIN_ZOOM = 1;
    private static final int DEFAULT_ZOOM = 3;
    private static final int MAX_ZOOM = 5;

    private static final int MIN_SIZE = 1;
    private static final int DEFAULT_SIZE = 3;
    private static final int MAX_SIZE = 5;

    private static final int MIN_PEN_WIDTH = 2;
    private static final int DEFAULT_PEN_WIDTH = 2;
    private static final int MAX_PEN_WIDTH = 5;

    private VBox sliders;

    public SliderController(String language) {
        this.myResources = ResourceBundle.getBundle(language);
        renderSliders();
    }

    public double getZoom() {
        return zoom.getValue();
    }

    public double getSizeValue() {
        return imagesize.getValue();
    }

    public double getPenWidth() {return penWidth.getValue();}

    public VBox getVBox() {
        return sliders;
    }

    public void updateZoom(Workspace current){
        current.getHabitat().setScaleX(getZoom()/3.0);
        current.getHabitat().setScaleY(getZoom()/3.0);
    }

    public void updateImageSize(Workspace current, int turtleId){
        current.getHabitat().getTurtle(turtleId).setScaleX(getSizeValue()/3.0);
        current.getHabitat().getTurtle(turtleId).setScaleY(getSizeValue()/3.0);
    }

    public void updatePenWidth(Workspace current, int )

    private void renderSliders() {
        sliders = new VBox();

        HBox allLabels = new HBox();
        addLabel("SizeSlider", allLabels);
        addLabel("ZoomSlider", allLabels);

        HBox allSliders = new HBox();
        imagesize = addAndReturnSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE, allSliders);
        zoom = addAndReturnSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM,
                allSliders);

        sliders.getChildren().add(allLabels);
        sliders.getChildren().add(allSliders);
    }

    private void addLabel(String key, HBox text) {
        Label tempLabel = new Label(myResources.getString(key));
        tempLabel.setMaxWidth(Double.MAX_VALUE);
        tempLabel.setAlignment(Pos.CENTER);
        text.setHgrow(tempLabel, Priority.ALWAYS);
        text.getChildren().add(tempLabel);
    }

    private Slider addAndReturnSlider(int min, int max, int def, HBox sliders) {
        Slider tempSlider = new Slider(min, max, def);
        sliders.setHgrow(tempSlider, Priority.ALWAYS);
        setSliderTicks(tempSlider);
        sliders.getChildren().add(tempSlider);
        return tempSlider;
    }

    private void setSliderTicks(Slider slider) {
        slider.setBlockIncrement(1);
        slider.setMajorTickUnit(1);
        slider.setMinorTickCount(0);
        slider.setShowTickLabels(true);
        slider.setSnapToTicks(true);
    }
}

