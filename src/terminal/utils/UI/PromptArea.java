package terminal.utils.UI;

import javafx.scene.control.TextArea;

public class PromptArea extends TextArea {
    private static int PREF_MARGIN_COUNT = 1;
    private static String defaultPromptText = ">>>";
    private double height;

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
