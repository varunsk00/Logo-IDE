package slogo.terminal;

import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.*;
import slogo.compiler.Compiler;
import slogo.terminal.utils.history.HistoryBuffer;
import slogo.terminal.utils.textLines.TestLine;

/**
 * TerminalController manages the communication between a TerminalView object and the compiler
 */
public class TerminalController {
    private TerminalView terminalView;
    private HistoryBuffer history;
    private Compiler compiler;


    /* TODO:
        1. where is the compiler
    * */

    /**
     * Constructor
     * @param view the TerminalView object
     */
    public TerminalController(TerminalView view){
        this.terminalView = view;
        Clipboard clipboard = Clipboard.getSystemClipboard();
        this.history = new HistoryBuffer();
        keyBinding();
    }

    /**
     * Change to a new language
     * @param newLanguage string for a language type
     */
    public void changeLanguage(String newLanguage){
        TestLine.changeLanguage(newLanguage);
    }

    public void setCompiler(Compiler c) {this.compiler = c;}

    private void keyBinding(){
        //set the focus to the scroll bar
        terminalView.getOutputPanel().addEventHandler(MouseEvent.ANY, e-> {
            Node node = terminalView.getOutputPanel().lookup(".scroll-bar");
           if (node instanceof ScrollBar){
               //System.out.println("scrollbar found");
               final ScrollBar bar = (ScrollBar) node;
               bar.requestFocus();
           }
        });

        // Control+C: copy the selected text
        KeyCombination CtrlC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        // Control+V: paste the selected text
        KeyCombination CtrlP = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY);

        terminalView.getInputArea().setOnKeyPressed(keyEvent -> {

            // Up | PageUp: get previous entry
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.PAGE_UP) {
                //System.out.println("page up");
                terminalView.resetInputPanel();
                displayTextTerminalInput(getPrevBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            // Down | PageDown: get next entry
            else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.PAGE_DOWN) {
                //System.out.println("page down");
                terminalView.resetInputPanel();
                displayTextTerminalInput(getNextBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            // Enter : send the input to compiler
            else if (keyEvent.getCode() == KeyCode.ENTER) {
                terminalView.getInputPanel().setPositionCaretAtEnding();
                appendToOutput(terminalView.getCurrentInput());
                history.addEntry(terminalView.getCurrentInput(), 1);
                appendToOutput(sendCurrentInput());
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
        //System.out.println(String.format("Displayed to output panel: %s",str));
        terminalView.displayTextstoOutput(str);
    }

    private String sendCurrentInput(){
        String userInput = terminalView.getCurrentInput().substring(terminalView.getUSER_INPUT_CODE().length());
        return compiler.execute(userInput);
        //System.out.println(userInput);
        //System.out.println("Unlinked to the compiler right now");
        //return null;
    }

    private String getPrevBufferEntry(){
        return history.getPrevEntry();
    }

    private String getNextBufferEntry(){
        return history.getNextEntry();
    }
}
