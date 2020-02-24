package terminal;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.*;
import terminal.utils.history.HistoryBuffer;

public class TerminalController {
    private Clipboard clipboard;
    private TerminalView terminalView;
    private HistoryBuffer history;


    /* TODO:
        1. where is the compiler
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
        //terminalView.getOutputPanel().setFocusTraversable(true);
        //terminalView.getOutputPanel().lookup(".scroll-bar").setMouseTransparent(false);
        terminalView.getOutputPanel().addEventHandler(MouseEvent.ANY, e-> {
            Node node = terminalView.getOutputPanel().lookup(".scroll-bar");
           if (node instanceof ScrollBar){
               System.out.println("scrollbar found");
               final ScrollBar bar = (ScrollBar) node;
               bar.requestFocus();
           }
        });
        KeyCombination CtrlC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        // Control+V: paste the selected text
        KeyCombination CtrlP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY);

        terminalView.getInputSection().setOnKeyPressed(keyEvent -> {
            System.out.println(keyEvent.getCode());
            if (keyEvent.getCode() == KeyCode.UP) {
                System.out.println("page up");
                terminalView.resetInputPanel();
                displayTextTerminalInput(getPrevBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            else if (keyEvent.getCode() == KeyCode.DOWN) {
                System.out.println("page down");
                terminalView.resetInputPanel();
                displayTextTerminalInput(getNextBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            else if (keyEvent.getCode() == KeyCode.ENTER) { //TODO: Enable the output of compiler message when the compiler is up
                System.out.println("enter");
                terminalView.getInputPanel().setPositionCaretAtEnding();
                appendToOutput(terminalView.getCurrentInput());
                history.addEntry(terminalView.getCurrentInput(), 1);
                //appendToOutput(sendCurrentInput());
                terminalView.resetInputPanel();
            }
            /*
            else if (CtrlC.match(keyEvent)) {
                String selectedText = terminalView.getSelectedText();
                if (!selectedText.equals("")) {
                    ClipboardContent clipboardContent = new ClipboardContent();
                    clipboardContent.putString(selectedText);
                    clipboard.setContent(clipboardContent);
                }
            }
            else if (CtrlP.match(keyEvent)) {
                displayTextTerminalInput(clipboard.getString());
            }
             */
        });
    }

    private void displayTextTerminalInput(String str){terminalView.setCurrentInput(str);}

    private void appendToOutput(String str){
        System.out.println(String.format("Displayed to output panel: %s",str));
        terminalView.displayTextstoOutput(str);
    }

    private String sendCurrentInput(){
        String userInput = terminalView.getCurrentInput().substring(terminalView.getUSER_INPUT_CODE().length());
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
