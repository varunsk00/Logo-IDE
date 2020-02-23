package terminal;

import javafx.scene.control.ScrollPane;
import terminal.utils.UI.InputPanel;
import terminal.utils.UI.OutputPanel;

//TODO: Caret Control

/**
 * terminalView generates the visualization of the terminal console.
 */
public class TerminalView extends ScrollPane {
    final static String BANNER_FILEPATH = "";
    final static String WELCOME_BANNER = "";

    // Identification of string type
    final static String ERRORMSG_CODE = "+898+";
    final static String SUCCESSMSG_CODE = "+200+";
    final static String BANNER_CODE = "+788+";
    final static String MSG_CODE = "+400+";
    final static String COMMANDLINE_CODE = "+101+";

    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    /**
     * Constructor
     */
    public TerminalView(){}

    /**
     * Sets the terminal to default style
     */
    public void setDefaultStyle(){}

    /**
     * Sets the size of the terminal
     * @param width width
     * @param height height
     */
    public void setTerminalSize(double width, double height){
        super.setMaxSize(width, height);
        super.setPrefSize(width, height);
    }

    /**
     * TODO: should keep the scroll bar at bottom every time the user input text?
     */
    public void setScrollBarToMax(){}

    public void displaySysMessage(String msg){}

    public void displayCommand(){}

    public String getSelectedText(){
        if (!inputPanel.getSelectedText().equals(""))
            return inputPanel.getSelectedText();
        if (!outputPanel.getSelectedText().equals(""))
            return inputPanel.getSelectedText();
        return "";
    }

    public String getCurrentInput(){
        return inputPanel.getText();
    }

    public void setCurrentInput(String str){
        inputPanel.setText(str);
    }

    public String getUSER_INPUT_CODE(){
        return inputPanel.getUSER_INPUT_CODE();
    }

    private void clearTerminal(){}

    private void initializeTerminal(){}

    private void printBanner(String FilePath){}

}
