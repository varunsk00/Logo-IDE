package slogo.terminal;

import javafx.scene.Node;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.*;
import slogo.compiler.Compiler;
import slogo.terminal.utils.history.HistoryBuffer;
import slogo.terminal.utils.textLines.TestLine;

import java.util.*;

/**
 * TerminalController manages the communication between a TerminalView object and the compiler
 */
public class TerminalController {
    private TerminalView terminalView;
    private HistoryBuffer history;
    private Compiler compiler;
    private int status;

    private List<String> commands;
    private List<String> messages;
    private static int cnt;

    /**
     * Constructor
     * @param view the TerminalView object
     */
    public TerminalController(TerminalView view){
        this.terminalView = view;
        Clipboard clipboard = Clipboard.getSystemClipboard();
        commands = new ArrayList<>();
        messages = new ArrayList<>();
        this.history = new HistoryBuffer();
        status = 0;
        cnt = 0;
        keyBinding();
    }

    /**
     * Change to a new language
     * @param newLanguage string for a language type
     */
    public void changeLanguage(String newLanguage){
        TestLine.changeLanguage(newLanguage);
    }

    public void setExternals(Compiler c) {this.compiler = c;}

    public List<String> getAllCommands(){System.out.println("???");return commands;}

    public List<String> getAllMessages(){return messages;}

    public int getStatus(){return status;}

    private void keyBinding(){
        //set the focus to the scroll bar
        terminalView.getOutputPanel().addEventHandler(MouseEvent.ANY, e-> {
            Node nodeVertical = terminalView.getOutputPanel().lookup(".scroll-bar:vertical");
           if (nodeVertical instanceof ScrollBar){
               final ScrollBar bar = (ScrollBar) nodeVertical;
               bar.requestFocus();
           }
        });

        // Control+C: copy the selected text
        KeyCombination CtrlC = new KeyCodeCombination(KeyCode.C, KeyCombination.CONTROL_ANY);
        // Control+V: paste the selected text
        KeyCombination CtrlV = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY);

        terminalView.getInputArea().setOnKeyPressed(keyEvent -> {

            // Up | PageUp: get previous entry
            if (keyEvent.getCode() == KeyCode.UP || keyEvent.getCode() == KeyCode.PAGE_UP) {
                terminalView.resetInputPanel();
                displayTextTerminalInput(getPrevBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            // Down | PageDown: get next entry
            else if (keyEvent.getCode() == KeyCode.DOWN || keyEvent.getCode() == KeyCode.PAGE_DOWN) {
                terminalView.resetInputPanel();
                displayTextTerminalInput(getNextBufferEntry());
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }
            // Enter : send the input to compiler
            else if (keyEvent.getCode() == KeyCode.ENTER) {
                terminalView.getInputPanel().setPositionCaretAtEnding();

                appendToOutput(terminalView.getCurrentInput());
                history.addEntry(terminalView.getCurrentInput(), 1); // add method now automatically resets the index
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
            */
            else if (CtrlV.match(keyEvent)) {
                terminalView.getInputPanel().setPositionCaretAtEnding();
            }

            Node nodeHorizontal = terminalView.getInputPanel().lookup(".scroll-bar:horizontal");
            if (nodeHorizontal instanceof ScrollBar){
                final ScrollBar bar = (ScrollBar) nodeHorizontal;
                bar.setValue(bar.getMax());
            }
        });
    }

    private void displayTextTerminalInput(String str){terminalView.setCurrentInput(str);}

    private void appendToOutput(String str){
        terminalView.displayTextstoOutput(str);
    }

    private String sendCurrentInput(){
        String userInput = terminalView.getCurrentInput().substring(terminalView.getUSER_INPUT_CODE().length());
        if (userInput.equals("")) return null;
        //System.out.println("### "+userInput);
        String systemMessage = compiler.execute(userInput);

        //update local memory
        commands.add(String.format("%d: %s", cnt++, userInput));
        messages.add(systemMessage);

        status ++;
        return systemMessage;
    }

    public void sendInput(String command){
        if (command.equals("")) return;
        appendToOutput(terminalView.formatInput(command));
        //System.out.println(terminalView.formatInput(command));
        //System.out.println(command);

        String systemMessage = compiler.execute(command);

        //update local memory
        commands.add(String.format("%d: %s", cnt++, command));
        messages.add(systemMessage);

        status ++;

        System.out.println(command);
        history.addEntry(command, 1); // add method now automatically resets the index
        appendToOutput(systemMessage);
    }

    private String getPrevBufferEntry(){
        return history.getPrevEntry();
    }

    private String getNextBufferEntry(){
        return history.getNextEntry();
    }
}
