package slogo.terminal.utils.UI;

import javafx.scene.control.TextArea;

/**
 * PromptArea generates the prompt are (literally, the ">>>").
 */
public class PromptArea extends TextArea {
    private static int PREF_MARGIN_COUNT = 0;
    private static String defaultPromptText = ">>>";
    private double height;

    /**
     * Constructor
     * @param height height (could be discarded)
     */
    public PromptArea(double height){
        super(defaultPromptText);

        this.height = height;
        setEditable(false);
        setStyle("-fx-font-family: \"Consolas\";");
        changeSize();
    }

    private void changeSize(){
        //setMaxHeight(height);
        //setMinHeight(height);
        setPrefColumnCount(PREF_MARGIN_COUNT);
    }

}
