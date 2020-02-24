package terminal.utils.UI;

import javafx.scene.control.TextArea;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//TODO ignore input
public class InputPanel extends HBox {
    private final String USER_INPUT_CODE = "U@@U";
    private final String CSS_FILEPATH = "/input_panel.css";
    private int width;
    private int height;
    private InputArea inputArea;
    private PromptArea promptArea;

    public InputPanel(int width, int height){
        super();
        initializeInputPanel();
        setSize(width, height);
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        HBox.setHgrow(inputArea, Priority.ALWAYS);
        setMaxSize(width, height);
        setPrefSize(width, height);
    }

    public void reset(){
        inputArea.setCaretZero();
        inputArea.clearInput();
    }

    public void setPositionCaretAtEnding(){inputArea.positionCaret(inputArea.getLength());}

    public void setPositionCaret(int pos){inputArea.positionCaret(pos);}

    public TextArea getInputSection(){return inputArea;}

    public void clearInput(){
        inputArea.clear();
    }

    public void setText(String text){
        inputArea.setText(text);
    }

    public String getText(){
        return String.format("%s%s", USER_INPUT_CODE, inputArea.getText());
    }

    public String getSelectedText(){
        return inputArea.getSelectedText();
    }

    public String getUSER_INPUT_CODE(){ return USER_INPUT_CODE; }

    private void initializeInputPanel(){
        promptArea = new PromptArea(height);
        inputArea = new InputArea(width, height);
        getChildren().addAll(promptArea, inputArea);
        getStylesheets().add(getClass().getResource(CSS_FILEPATH).toExternalForm());
    }
}
