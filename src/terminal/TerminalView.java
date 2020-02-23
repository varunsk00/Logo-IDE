package terminal;

import javafx.scene.control.ScrollPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import terminal.utils.UI.InputPanel;
import terminal.utils.UI.OutputPanel;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

//TODO: Caret Control

/**
 * terminalView generates the visualization of the terminal console.
 */
public class TerminalView extends ScrollPane {
    final static int INPUT_PANEL_HEIGHT = 40; //TODO: Empirical adjustment, needs calculation of the font size
    final static String BANNER_FILEPATH = "banners/";
    final static String WELCOME_BANNER = "welcome";

    private InputPanel inputPanel;
    private OutputPanel outputPanel;

    /**
     * Constructor
     */
    public TerminalView(int width, int height){initializeTerminal(width, height);}


    /**
     * Sets the size of the terminal
     * @param width width
     * @param height height
     */
    public void setTerminalSize(double width, double height){
        super.setMaxSize(width, height);
        super.setPrefSize(width, height);
    }

   public void displayTextstoOutput(String textLine){
        outputPanel.addTexts(textLine);
   }

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

    private void clearTerminal(){
        inputPanel.clearInput();
        outputPanel.clearTexts();
    }

    private void initializeTerminal(int width, int height){
        outputPanel = new OutputPanel(width, height);
        inputPanel = new InputPanel(width, INPUT_PANEL_HEIGHT);
        VBox box = new VBox(outputPanel, inputPanel); //TODO: do we really need this box;
        getChildren().add(box);

        printBanner(String.format("%s%s", BANNER_FILEPATH, WELCOME_BANNER));
    }

    /**
     * TODO: should keep the scroll bar at bottom every time the user input text?
     */
    public void setScrollBarToMax(){}

    private void printBanner(String filePath){displayTextstoOutput(filePath);}

    private String readBannerfromFile(String filePath){
        try {
            return new String(Files.readAllBytes(Paths.get(filePath)));
        } catch (IOException e) {
            System.out.println("Banner file could not be founded.");
            return "Welcome to the Turtle Parser.";
        }
    }
}
