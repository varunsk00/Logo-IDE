package terminal;

import javafx.scene.input.*;
import terminal.utils.history.HistoryBuffer;

public class TerminalController {
    private Clipboard clipboard;
    private TerminalView terminalView;
    private HistoryBuffer history;


    /* TODO:
        1. where is the compiler
        4. control the cursor
    * */

    public TerminalController(TerminalView view){
        this.terminalView = view;
        this.clipboard = Clipboard.getSystemClipboard();
        this.history = new HistoryBuffer();
        keyBinding();
    }

    private void keyBinding(){

        // PageUp: previous entry
        // PageDown: next entry
        // Enter: send the input to compiler
        // Control+C: copy the selected text
        KeyCombination CtrlC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        // Control+V: paste the selected text
        KeyCombination CtrlP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY);
        terminalView.getInputSection().setOnKeyPressed(keyEvent -> {
            System.out.println("gewh");
            if (keyEvent.getCode() == KeyCode.PAGE_UP) {
                displayTextTerminalInput(getPrevBufferEntry());
            } else if (keyEvent.getCode() == KeyCode.PAGE_DOWN) {
                displayTextTerminalInput(getNextBufferEntry());
            } else if (keyEvent.getCode() == KeyCode.ENTER) {
                appendToOutput(terminalView.getCurrentInput());
                terminalView.resetInputPanel();
                appendToOutput(sendCurrentInput());
            } else if (CtrlC.match(keyEvent)) {
                String selectedText = terminalView.getSelectedText();
                if (!selectedText.equals("")) {
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(selectedText);
                    clipboard.setContent(clipboardContent);
                }
            } else if (CtrlP.match(keyEvent)) {
                displayTextTerminalInput(clipboard.getString());
            }
        });
    }

    private void displayTextTerminalInput(String str){terminalView.setCurrentInput(str);}

    private void appendToOutput(String str){
        terminalView.displayTextstoOutput(str);
    }

    private String sendCurrentInput(){
        String userInput = terminalView.getCurrentInput().substring(terminalView.getUSER_INPUT_CODE().length());
        history.addEntry(userInput, 1);
        System.out.println(userInput);
        System.out.println("Unlinked to the compiler right now");
        return null;
    }

    private String getPrevBufferEntry(){
        return history.getPrevEntry();
    }

    private String getNextBufferEntry(){
        return history.getNextEntry();
    }
}
