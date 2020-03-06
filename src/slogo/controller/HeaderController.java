package slogo.controller;

import javafx.scene.layout.VBox;

import java.io.FileNotFoundException;

public class HeaderController extends VBox {
    private ButtonController buttons;
    private SliderController sliders;

    public HeaderController(String language) throws FileNotFoundException {
        buttons = new ButtonController(language);
        sliders = new SliderController(language);
        sliders.getVBox().getStyleClass().add("slider-box");
        getChildren().addAll(buttons.getHBox(), sliders.getVBox());
    }

    public ButtonController getButtons(){
        return buttons;
    }

    public SliderController getSliders(){
        return sliders;
    }
}
