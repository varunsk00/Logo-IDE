package terminal.utils.UI;

import javafx.scene.control.TextArea;


public class PromptArea extends TextArea {
    private static int HEIGHT_TEXTFIELD = 25;
    private static int PREF_MARGIN_COUNT = 1;
    private static String defaultPromptText = ">>>";

    public PromptArea(){
        super(defaultPromptText);
        setEditable(false);
        changeSize();
    }

    private void changeSize(){
        setMaxHeight(HEIGHT_TEXTFIELD);
        setMinHeight(HEIGHT_TEXTFIELD);
        setPrefColumnCount(PREF_MARGIN_COUNT);
    }

}
