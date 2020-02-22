package terminal.utils.UI;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

//TODO ignore input
public class InputPanel extends HBox {
    private final int PROMPT_WIDTH = 5; //TODO: empirical adjusstment
    private final String CSS_FILEPATH = "/input_panel.css";
    private final String FONT_FILEPATH = "/font.css";
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

    public void clearInput(){
        inputArea.clear();
    }

    public void setText(String text){
        inputArea.setText(text);
    }

    public String getText(){
        return inputArea.getText();
    }

    private void initializeInputPanel(){
        promptArea = new PromptArea(height);
        inputArea = new InputArea(width, height);
        getChildren().addAll(promptArea, inputArea);
        getStylesheets().add(getClass().getResource(CSS_FILEPATH).toExternalForm());
        getStylesheets().add(getClass().getResource(FONT_FILEPATH).toExternalForm());
    }
}
