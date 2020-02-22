package terminal.utils.UI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//TODO ignore input
public class InputPanel extends HBox {
    private final int PROMPT_WIDTH = 5; //TODO: empirical adjusstment
    private final String CSS_FILEPATH = "/css/input_panel.css";
    private int width;
    private int height;
    private InputField inputField;
    private PromptArea promptArea;

    public InputPanel(int width, int height){
        super();
        initializeInputPanel();
        setSize(width, height);
    }

    public void setSize(int width, int height){
        this.width = width;
        this.height = height;
        HBox.setHgrow(inputField, Priority.ALWAYS);
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
        promptArea = new PromptArea(height);
        inputField = new InputField(width, height);
        getChildren().addAll(promptArea, inputField);
        getStylesheets().add(getClass().getResource(CSS_FILEPATH).toExternalForm());
    }
}
