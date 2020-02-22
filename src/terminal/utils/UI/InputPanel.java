package terminal.utils.UI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;

public class InputPanel extends HBox {
    private final int PROMPT_WIDTH = 5; //TODO: empirical adjusstment
    private int width;
    private int height;
    private InputField inputField;
    private PromptLabel promptLabel;

    public InputPanel(int width, int height){
        super();
        setSize(width, height);
        initializeInputPanel();
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        setMaxSize(width, height);
        setPrefSize(width, height);
    }

    public void clearInput(){
        inputField.clear();
    }

    public void setText(String text){
        inputField.setText(text);
    }

    public String getText(){
        return inputField.getText();
    }

    private void initializeInputPanel(){
        promptLabel = new PromptLabel();
        inputField = new InputField(width);
        this.getChildren().addAll(promptLabel, inputField);
    }
}
