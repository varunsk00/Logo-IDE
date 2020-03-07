package slogo.terminal;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;
import java.util.Random;
import java.util.Scanner;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.input.KeyCombination;
import javafx.scene.input.MouseEvent;
import slogo.compiler.parser.Compiler;
import slogo.terminal.utils.history.HistoryBuffer;
import slogo.terminal.utils.textLines.TestLine;
import slogo.turtle.TurtleHabitat;

/**
 * TerminalController manages the communication between a TerminalView object and the compiler
 */
public class TerminalController {
  final private String USER_SAVE_FILE = "src/slogo/resources/userspace/";
  final private String SLOGO_SAVED_MSG = " slogo file saved successfully.";
  final private String PREF_SAVED_MSG = " preference file saved successfully.";
  private static int STATUS_MAX = 5000;
  private TerminalView terminalView;
  private TurtleHabitat habitat;
  private HistoryBuffer history;
  private Compiler compiler;
  private int status;
  private int saveCnt;

  /**
   * Constructor
   *
   * @param view the TerminalView object
   */
  public TerminalController(TerminalView view) {
    this.terminalView = view;
    this.history = new HistoryBuffer();
    status = 0;
    saveCnt = 0;
    keyBinding();
  }

  /**
   * Change to a new language
   *
   * @param newLanguage string for a language type
   */
  public void changeLanguage(String newLanguage) {
    TestLine.changeLanguage(newLanguage);
  }

  /**
   * Processes the input string that comes outside from the terminal (variable explore)
   *
   * @param command input command
   */
  public void sendInput(String command) {
    if (command.equals("")) {
      return;
    }

    appendToOutput(terminalView.formatInput(command));

    habitat.saveToStack();
    String systemMessage = compiler.execute(command);

    //update local memory
    history.addHistory(command, systemMessage);

    updateStatus(); // trigger the update of variable explore

    history.addBufferEntry(command, 1); // add method now automatically resets the index
    appendToOutput(systemMessage);
  }

  public void sendFileInput(File file) throws FileNotFoundException {
    sendInput(readFromFile(file));
  }

  public void setSize(int width, int height) {
    terminalView.setSize(width, height);
  }

  public void setCompiler(Compiler c) {
    this.compiler = c;
  }

  public void setHabitat(TurtleHabitat h) {
    this.habitat = h;
  }

  public List<String> getAllCommands() {
    return history.getCommands();
  }

  public List<String> getAllMessages() {
    return history.getMessages();
  }

  public int getStatus() {
    return status;
  }

  private void keyBinding() {
    //set the focus to the scroll bar
    terminalView.getOutputPanel().addEventHandler(MouseEvent.ANY, e -> {
      final ScrollBar barVertical = (ScrollBar) terminalView.getOutputPanel()
          .lookup(".scroll-bar:vertical");
      barVertical.requestFocus();
    });

    //final ScrollBar barHorizontal = (ScrollBar) terminalView.getInputPanel().lookup(".scroll-bar:horizontal");
    //barHorizontal.setValue(barHorizontal.getMax());

    // Control+S: save
    KeyCombination CtrlS = new KeyCodeCombination(KeyCode.S, KeyCombination.CONTROL_ANY);
    // Control+V: paste the selected text
    KeyCombination CtrlV = new KeyCodeCombination(KeyCode.P, KeyCombination.CONTROL_ANY);
    // Control+Z: undo
    KeyCombination CtrlZ = new KeyCodeCombination(KeyCode.Z, KeyCombination.CONTROL_ANY);

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
        history.addBufferEntry(terminalView.getCurrentInput(),
            1); // add method now automatically resets the index
        System.out.println(terminalView.getCurrentInput());
        appendToOutput(sendCurrentInput());

        terminalView.resetInputPanel();

      }
      else if (CtrlS.match(keyEvent)) {
        try {
          saveInputToFile(compiler.getEnteredText());
        } catch (IOException e) {
          System.out.println("Error: The output file is missing.");
        }
      }
      else if (CtrlV.match(keyEvent)) {
        terminalView.getInputPanel().setPositionCaretAtEnding();
      } else if (CtrlZ.match(keyEvent)) {
        terminalView.getOutputPanel().undoEntry();
        compiler.undo();
        habitat.undoPen();
        clearLastHistoryEntry();
      }
    });
  }

  private void displayTextTerminalInput(String str) {
    terminalView.setCurrentInput(str);
  }

  private void clearLastHistoryEntry() {
    history.removeLastHistory();
    updateStatus(); //to trigger the update of variable explore
  }

  private void appendToOutput(String str) {
    terminalView.displayTextstoOutput(str);
  }

  private String sendCurrentInput() {
    String userInput = terminalView.getCurrentInput()
        .substring(terminalView.getUSER_INPUT_CODE().length());
    if (userInput.equals("")) {
      return null;
    }

    habitat.saveToStack();
    String systemMessage = compiler.execute(userInput);

    //update local memory
    history.addHistory(userInput, systemMessage);

    updateStatus();
    return systemMessage;
  }

  private String getPrevBufferEntry() {
    return history.getPrevBufferEntry();
  }

  private String getNextBufferEntry() {
    return history.getNextBufferEntry();
  }

  private void updateStatus() {
    Random rand = new Random();
    status = rand.nextInt(STATUS_MAX);
  }

  private String readFromFile(File dataFile) throws FileNotFoundException {
    String input = "";
    Scanner scanner = new Scanner(dataFile);
    while (scanner.hasNext()) {
      String line = scanner.nextLine();
      if (line.startsWith("#")) {
        continue;
      }
      input += line + " ";
    }
    return input;
  }

  private void saveInputToFile(String text) throws IOException {
    String filename = String.format("%d.logo", ++saveCnt);
    String filepath = String.format("%s%s", USER_SAVE_FILE, filename);

    File newOuputFile = new File(filepath);
    if (newOuputFile.createNewFile()){
      System.out.println(String.format("%s File created.", filename));
    }
    else {
      System.out.println(String.format("%s already exists.", filename));
    }
    appendToOutput(String.format("%s%s", filename, SLOGO_SAVED_MSG));
    FileWriter fileWriter = new FileWriter(newOuputFile);
    fileWriter.write(text);
    fileWriter.close();

  }
}
