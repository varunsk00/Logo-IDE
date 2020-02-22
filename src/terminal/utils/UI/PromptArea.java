package terminal.utils.UI;

import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

public class PromptArea extends TextArea {
    private static String defaultPromptText = ">>>";

    public PromptArea(){
        super(defaultPromptText);
        setEditable(false);
        setMaxSize(3, 3);
        setPrefHeight(3);
    }

}
