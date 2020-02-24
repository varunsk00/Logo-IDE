package slogo.terminal.utils.textLines;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;
import java.util.regex.Pattern;

/**
 * TestLine is the cell factory that generates a TextFlow object accordingly to the type of the text.
 */
public class TestLine extends ListCell<String> {
    private final static String OTHER_TYPE_CODE = "OTHER_TYPE";
    private final static String ERROR_MSG_CODE = "ERROR_MSG";
    private final static String USER_INPUT_CODE = "USER_INPUT";
    private final static String USER_CODE = "U@@U";
    private final static String INPUT_PROMPT = ">>>";

    private final static String RESERVED_COMMAND_CODE = "RESERVED_COMMAND";
    private final static String DIGITS_CODE = "DIGITS";
    private final static String COMMENT_CODE = "COMMENT";
    private final static String OTHER_COMMAND_CODE = "OTHER_COMMAND";


    private final static String SEPARATOR = " ";
    private final static String LOCAL_RESOURCE_MATCH_DICT = String.format("%s.regex_type", TestLine.class.getPackageName());
    private final static String RESERVE_WORD_DICT_PATH = "slogo/resources/languages/";
    private final static String regexDigits = "-?[0-9]+(?:\\.[0-9]+)?";
    private final static String regexComment = "^#.*";

    private List<Map.Entry<String, Pattern>> matchDictionary;
    private static String currentLanguage;

    {setContentDisplay(ContentDisplay.GRAPHIC_ONLY); setStyle("-fx-font-family: \"Consolas\";"); currentLanguage="English";}

    @Override
    protected void updateItem(String str, boolean empty){
        //System.out.println("updating a new item "+str);
        super.updateItem(str, empty);
        if (!checkEmpty(str)){
            //System.out.println(str+" is not empty");
            setGraphic(createTextFlow(str));
        }
        else{
            //System.out.println(str+" is empty");
            setGraphic(null);
        }
    }

    /**
     * Change to new language
     * @param newLanguage language string
     */
    static public void changeLanguage(String newLanguage){
        currentLanguage = newLanguage;
    }

    private Node createTextFlow(String str){
        //determines the type of the text string
        matchDictionary = initializeDictionary(LOCAL_RESOURCE_MATCH_DICT);
        //System.out.println(str+ "is "+getType(str));
        if (getType(str).equals(ERROR_MSG_CODE)){
            return createErrorMsgFlow(str);
        }
        else if (getType(str).equals(USER_INPUT_CODE)){
            return createUserInputFlow(str);
        }
        else{
            return createOtherFlow(str);
        }
    }

    private Node createErrorMsgFlow(String textLine){
        String[] textsStr = textLine.split(SEPARATOR);
        TextFlow flow = new TextFlow();

        for (String textStr: textsStr){
            ColorText text = new ColorText(textStr, ERROR_MSG_CODE);
            flow.getChildren().addAll(text, createSpacer());
        }
        return flow;
    }

    private Node createUserInputFlow(String textLine){
        //System.out.println(textLine+" this is a user input");
        textLine = String.format("%s%s",INPUT_PROMPT, textLine);
        String[] textsStr = stripInputText(textLine).split(SEPARATOR);
        TextFlow flow = new TextFlow();

        //adding initial prompt
        flow.getChildren().addAll(new ColorText(INPUT_PROMPT, getTextStrType(INPUT_PROMPT)),
                                  createSpacer(), createSpacer(), createSpacer());

        boolean commentFlag = isComment(stripInputText(textLine));

        for (String textStr: textsStr){
            //System.out.println(textStr+" "+getTextStrType(textStr));
            ColorText text;
            if (commentFlag){
                text = new ColorText(textStr, COMMENT_CODE);
            }
            else{
                text = new ColorText(textStr, getTextStrType(textStr));
            }
            flow.getChildren().addAll(text, createSpacer());
        }
        return flow;
    }

    private Node createOtherFlow(String textLine){

        TextFlow flow = new TextFlow();

        Text text = new Text(textLine);
        text.setFill(Color.WHITE);

        flow.getChildren().add(text);

        /*
        String[] textsStr = textLine.split(SEPARATOR);
        for (String textStr: textsStr){
            ColorText text = new ColorText(textStr, OTHER_TYPE_CODE);
            flow.getChildren().addAll(text, createSpacer());
        }*/

        return flow;
    }

    private String stripInputText(String input){
        //System.out.println(input);
        return input.substring(INPUT_PROMPT.length()+USER_CODE.length());
    }

    private Text createSpacer(){return new Text(SEPARATOR);}

    private boolean checkEmpty(String str){
        return str==null || str.equals(""); //|| !isEmpty();
    }

    private String getTextStrType(String str){
        if (isDigits(str)) return DIGITS_CODE;
        if (isReservedWord(str)) return RESERVED_COMMAND_CODE;
        return OTHER_COMMAND_CODE;
    }

    private boolean isDigits(String str){
        return match(str, Pattern.compile(regexDigits, Pattern.CASE_INSENSITIVE));
    }

    private boolean isComment(String str) {
        //System.out.println(str);
        //System.out.println(match(str, Pattern.compile(regexComment, Pattern.CASE_INSENSITIVE)));
        return match(str, Pattern.compile(regexComment, Pattern.CASE_INSENSITIVE));}

    private boolean isReservedWord(String str){
        List<Map.Entry<String, Pattern>> reserveWordDictionary = initializeDictionary(String.format("%s%s",RESERVE_WORD_DICT_PATH, currentLanguage));

        for (Map.Entry<String, Pattern> e: reserveWordDictionary){
            //System.out.println(str+" "+e.getValue());
            if (match(str.toLowerCase(), e.getValue()) || match(str.toUpperCase(), e.getValue())) {
                return true;
            }
        }
        return false;
    }

    private List<Map.Entry<String, Pattern>> initializeDictionary(String resourcePath){
        ResourceBundle resources = ResourceBundle.getBundle(resourcePath);
        List<Map.Entry<String, Pattern>> dict = new ArrayList<>();
        for (String key: Collections.list(resources.getKeys())){
            String regex = resources.getString(key);
            //System.out.println(key+","+regex);
            dict.add(new AbstractMap.SimpleEntry<>(key,
                    Pattern.compile(regex, Pattern.CASE_INSENSITIVE)));
        }
        return dict;
    }

    private String getType(String text){
        for (Map.Entry<String, Pattern> e: matchDictionary){
            if (find(text, e.getValue())){
                return e.getKey();
            }
        }
        return OTHER_TYPE_CODE;
    }

    private boolean find (String text, Pattern regex){
        return regex.matcher(text).find();
    }

    private boolean match (String text, Pattern regex){
        return regex.matcher(text).matches();
    }
}
