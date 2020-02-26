package slogo.controller;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Slider;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.ResourceBundle;

/**
 * SliderControls class serves as a controller unit, allowing the user to input two variables:
 * simulation speed and number of frames to skip. SliderControls works in conjunction with
 * ButtonControls via the CAController class. Once a value for Frames to Skip has been set in the
 * SliderControls through a Slider, the CAController has an encapsulated ButtonControls class with a
 * Button to press to add the effect of the value into the simulation. Display is dynamic and
 * shrinks/grows to increases/decreases in the width of the Window Every simulation needs a
 * SliderControls, which should be instantiated in the start() method in Main and added to the
 * bottom of the BorderPane
 *
 * @author Eric Doppelt
 */
public class SliderController {

    private ResourceBundle myResources;

    private Slider zoom;
    private Slider imagesize;

    private static final int MIN_ZOOM = 1;
    private static final int DEFAULT_ZOOM = 3;
    private static final int MAX_ZOOM = 5;

    private static final int MIN_SIZE = 1;
    private static final int DEFAULT_SIZE = 3;
    private static final int MAX_SIZE = 5;

    private VBox footer;

    public SliderController(String language) {
        this.myResources = ResourceBundle.getBundle(language);
        renderFooter();
    }

    public double getZoom() {
        return zoom.getValue();
    }

    public double getSizeValue() {
        return imagesize.getValue();
    }

    public VBox getFooter() {
        return footer;
    }

    private void renderFooter() {
        footer = new VBox();

        HBox allLabels = new HBox();
        addLabel("SizeSlider", allLabels);
        addLabel("ZoomSlider", allLabels);

        //addReturnSlider() adds the Sliders to the frame and then returns them as instance variables for later access
        HBox allSliders = new HBox();
        imagesize = addAndReturnSlider(MIN_SIZE, MAX_SIZE, DEFAULT_SIZE, allSliders);
        zoom = addAndReturnSlider(MIN_ZOOM, MAX_ZOOM, DEFAULT_ZOOM,
                allSliders);

        footer.getChildren().add(allLabels);
        footer.getChildren().add(allSliders);
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

