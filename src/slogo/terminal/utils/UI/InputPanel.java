package slogo.terminal.utils.UI;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//TODO ignore input

/**
 * InputPanel assembles InputArea and the PromptArea to build the input panel at the bottom of the terminal
 */
public class InputPanel extends HBox {
    private final String USER_INPUT_CODE = "U@@U";
    private final String CSS_FILEPATH = "slogo/resources/styleSheets/input_panel.css";
    private int width;
    private int height;
    private InputArea inputArea;
    private PromptArea promptArea;

    /**
     * Constructor
     * @param width width
     * @param height height
     */
    public InputPanel(int width, int height){
        super();
        initializeInputPanel();
        setSize(width, height);
    }

    /**
     * Resizes the panel
     * @param width new width
     * @param height new height
     */
    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        HBox.setHgrow(inputArea, Priority.ALWAYS);
        setMaxSize(width, height);
        setPrefSize(width, height);
    }

    /**
     * Resets/Clears the content and sets the text caret to the beginning
     */
    public void reset(){
        inputArea.setCaretZero();
        inputArea.clearInput();
    }

    /**
     * Moves the caret to the end of text
     */
    public void setPositionCaretAtEnding(){inputArea.positionCaret(inputArea.getLength());}

    /**
     * Returns the InputArea object
     * @return TextArea
     */
    public TextArea getInputArea(){return inputArea;}

    /**
     * Clears the content of the input area
     */
    public void clearInput(){
        inputArea.clear();
    }

    /**
     * Sets the content of the input area to a given string
     * @param text new string text
     */
    public void setText(String text){
        inputArea.setText(text);
    }

    /**
     * Gets the user input (with a user input code for the convenience of identification)
     * @return text string
     */
    public String getText(){
        return String.format("%s%s", USER_INPUT_CODE, inputArea.getInputText());
    }

    /**
     * [unimplemented]
     * Returns the selected highlight text
     * @return text string
     */
    public String getSelectedText(){
        return inputArea.getSelectedText();
    }

    /**
     * Returns the user input code
     * @return string
     */
    public String getUSER_INPUT_CODE(){ return USER_INPUT_CODE; }

    private void initializeInputPanel(){
        promptArea = new PromptArea(height);
        inputArea = new InputArea(width, height);
        getChildren().addAll(promptArea, inputArea);
        getStylesheets().add(CSS_FILEPATH);
    }
}
