package terminal.utils.UI;

import javafx.scene.control.TextArea;
import javafx.scene.text.Font;

public class InputArea extends TextArea {
    private double height;
    private double width;

    public InputArea(double width, double height){
        super();
        this.width = width;
        this.height = height;
        setStyle("-fx-font-family: \"Consolas\";");
    }

    private void changeSize(){
        setPrefHeight(height);
        setPrefWidth(width);
    }
}
