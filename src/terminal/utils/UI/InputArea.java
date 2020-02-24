package terminal.utils.UI;

import javafx.scene.control.TextArea;

//cursor
public class InputArea extends TextArea {
    private double height;
    private double width;

    public InputArea(double width, double height){
        super();
        this.width = width;
        this.height = height;
        // font can only be set inline
        setStyle("-fx-font-family: \"Consolas\";");
    }

    public String getInputText() {
        return getText().replace("\n", "");
    }

    public void setCaretZero(){
        positionCaret(0);
    }

    public void clearInput(){
        setText("");
    }
    private void changeSize(){
        setPrefHeight(height);
        setPrefWidth(width);
    }


}
