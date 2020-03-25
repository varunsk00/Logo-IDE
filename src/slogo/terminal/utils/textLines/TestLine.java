package slogo.terminal.utils.textLines;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.regex.Pattern;
import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

/**
 * TestLine is the cell factory that generates a TextFlow object accordingly to the type and the content of the
 * text.
 * @author Qiaoyi Fang
 */
public class TestLine extends ListCell<String> {

  private final static String OTHER_TYPE_CODE = "OTHER_TYPE";
  private final static String ERROR_MSG_CODE = "ERROR_MSG";
  private final static String USER_INPUT_CODE = "USER_INPUT";
  private final static String TERMINAL_USER_CODE = "U@@U"; //NON_TERMINAL_USER_CODE = "U--U";
  private final static String TERMINAL_INPUT_PROMPT = ">>>";
  private final static String NON_TERMINAL_INPUT_PROMPT = "<<<";

  private final static String RESERVED_COMMAND_CODE = "RESERVED_COMMAND";
  private final static String DIGITS_CODE = "DIGITS";
  private final static String COMMENT_CODE = "COMMENT";
  private final static String OTHER_COMMAND_CODE = "OTHER_COMMAND";


  private final static String SEPARATOR = "\\s+";
  private final static String SPACER = " ";
  private final static String LOCAL_RESOURCE_MATCH_DICT = String
      .format("%s.regex_type", TestLine.class.getPackageName());
  private final static String RESERVE_WORD_DICT_PATH = "slogo/resources/languages/syntax/";
  private final static String regexDigits = "-?[0-9]+(?:\\.[0-9]+)?";
  private final static String regexComment = "^#.*";
  private static String currentLanguage;
  private List<Map.Entry<String, Pattern>> matchDictionary;

  {
    setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
    setStyle("-fx-font-family: \"Consolas\";");
    currentLanguage = "English";
  }

  /**
   * Constructor
   * @param width the preferred width of the TextLine
   */
  public TestLine(double width) {
    super();
    setWrapText(true);
    setPrefWidth(width);
  }

  /**
   * Updates the parsing language
   *
   * @param newLanguage language string
   */
  static public void changeLanguage(String newLanguage) {
    currentLanguage = newLanguage;
  }

  @Override
  protected void updateItem(String str, boolean empty) {
    super.updateItem(str, empty);
    if (!checkEmpty(str)) {
      setGraphic(createTextFlow(str));
    } else {
      setGraphic(null);
    }
  }

  private Node createTextFlow(String str) {
    //determines the type of the text string
    matchDictionary = initializeDictionary(LOCAL_RESOURCE_MATCH_DICT);

    if (getType(str).equals(ERROR_MSG_CODE)) {
      return createErrorMsgFlow(str);
    } else if (getType(str).equals(USER_INPUT_CODE)) {
      return createUserInputFlow(str);
    } else {
      return createOtherFlow(str);
    }
  }

  private Node createErrorMsgFlow(String textLine) {
    String[] textsStr = textLine.split(SEPARATOR);
    TextFlow flow = new TextFlow();

    for (String textStr : textsStr) {
      ColorText text = new ColorText(textStr, ERROR_MSG_CODE);
      flow.getChildren().addAll(text, createSpacer());
    }
    return flow;
  }

  private Node createUserInputFlow(String textLine) {
    TextFlow flow = new TextFlow();
    flow.getChildren()
        .addAll(new ColorText(getPrompt(textLine), getTextStrType(getPrompt(textLine))),
            createSpacer(), createSpacer(), createSpacer());

    String[] textsStr = stripInputText(textLine).split(SEPARATOR);

    //adding initial prompt
    boolean commentFlag = isComment(stripInputText(textLine));

    for (String textStr : textsStr) {
      ColorText text;
      if (commentFlag) {
        text = new ColorText(textStr, COMMENT_CODE);
      } else {
        text = new ColorText(textStr, getTextStrType(textStr));
      }
      flow.getChildren().addAll(text, createSpacer());
    }
    return flow;
  }

  private Node createOtherFlow(String textLine) {

    TextFlow flow = new TextFlow();

    Text text = new Text(textLine);
    text.setFill(Color.WHITE);

    flow.getChildren().add(text);

    return flow;
  }

  private String stripInputText(String input) {
    return input.substring(TERMINAL_USER_CODE.length());
  }

  private Text createSpacer() {
    return new Text(SPACER);
  }

  private boolean checkEmpty(String str) {
    return str == null || str.equals("");
  }

  private String getTextStrType(String str) {

    if (isDigits(str)) {
      return DIGITS_CODE;
    }
    if (isReservedWord(str)) {
      return RESERVED_COMMAND_CODE;
    }
    return OTHER_COMMAND_CODE;
  }

  private boolean isDigits(String str) {
    return match(str, Pattern.compile(regexDigits, Pattern.CASE_INSENSITIVE));
  }

  private boolean isComment(String str) {
    return match(str, Pattern.compile(regexComment, Pattern.CASE_INSENSITIVE));
  }

  private boolean isReservedWord(String str) {
    List<Map.Entry<String, Pattern>> reserveWordDictionary = initializeDictionary(
        String.format("%s%s", RESERVE_WORD_DICT_PATH, currentLanguage));

    for (Map.Entry<String, Pattern> e : reserveWordDictionary) {

      if (match(str.toLowerCase(), e.getValue()) || match(str.toUpperCase(), e.getValue())) {
        return true;
      }
    }
    return false;
  }

  private List<Map.Entry<String, Pattern>> initializeDictionary(String resourcePath) {
    ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
    List<Map.Entry<String, Pattern>> dict = new ArrayList<>();
    for (String key : Collections.list(resources.getKeys())) {
      String regex = resources.getString(key);
      dict.add(new AbstractMap.SimpleEntry<>(key,
          Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
    }
    return dict;
  }

  private String getType(String text) {
    for (Map.Entry<String, Pattern> e : matchDictionary) {
      if (find(text, e.getValue())) {
        return e.getKey();
      }
    }
    return OTHER_TYPE_CODE;
  }

  private String getPrompt(String textLine) {
    if (isTerminalInput(textLine)) {
      return TERMINAL_INPUT_PROMPT;
    } else {
      return NON_TERMINAL_INPUT_PROMPT;
    }
  }

  private boolean isTerminalInput(String text) {
    return find(text, Pattern.compile(TERMINAL_USER_CODE, Pattern.CASE_INSENSITIVE));
  }

  private boolean find(String text, Pattern regex) {
    return regex.matcher(text).find();
  }

  private boolean match(String text, Pattern regex) {
    return regex.matcher(text).matches();
  }
}
