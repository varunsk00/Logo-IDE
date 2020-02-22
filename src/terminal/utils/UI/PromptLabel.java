package terminal.utils.UI;

import javafx.scene.control.Label;

public class PromptLabel extends Label {
    private static String defaultPromptText = ">>>";

    public PromptLabel(){
        super(defaultPromptText);
    }

}
