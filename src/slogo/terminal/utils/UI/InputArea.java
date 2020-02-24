package slogo.terminal.utils.UI;

import javafx.scene.control.TextArea;

/**
 * InputArea generates the textarea that user could put in commands
 */
public class InputArea extends TextArea {
    private double height;
    private double width;

    /**
     * Constructor
     * @param width width
     * @param height height
     */
    public InputArea(double width, double height){
        super();
        this.width = width;
        this.height = height;
        // font can only be set inline
        setStyle("-fx-font-family: \"Consolas\";");
    }

    /**
     * Returns the user input text
     * @return text string
     */
    public String getInputText() {
        return getText().replace("\n", "");
    }

    /**
     * Sets the text caret to the first position of the text
     */
    public void setCaretZero(){
        positionCaret(0);
    }

    /**
     * Clears any input
     */
    public void clearInput(){
        setText("");
    }

    private void changeSize(){
        setPrefHeight(height);
        setPrefWidth(width);
    }

}
