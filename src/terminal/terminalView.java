package terminal;

import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import terminal.utils.UI.InputArea;
import terminal.utils.UI.PromptPanel;
import terminal.utils.UI.TerminalPast;

/**
 * terminalView generates the visualization of the terminal console.
 */
public class terminalView extends ScrollPane {
    final static String BANNER_FILEPATH = "";
    final static String WELCOME_BANNER = "";

    // Identification of string type
    final static String ERRORMSG_CODE = "+898+";
    final static String SUCCESSMSG_CODE = "+200+";
    final static String BANNER_CODE = "+788+";
    final static String MSG_CODE = "+400+";
    final static String COMMANDLINE_CODE = "+101+";

    private PromptPanel consolePrompt;
    private InputArea inputArea;
    private TerminalPast terminalPast;

    /**
     * Constructor
     */
    public terminalView(){}

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

    private void clearTerminal(){}

    private void initializeTerminal(){}

    private void printBanner(String FilePath){}

}
