package terminal.utils.UI;

import javafx.scene.control.TextArea;

public class InputField extends TextArea {
    private double height;
    private double width;

    public InputField(double width, double height){
        super();
        this.width = width;
        this.height = height;
    }

    private void changeSize(){
        setPrefHeight(height);
        setPrefWidth(width);
    }
}
