package terminal.utils.textLines;

import javafx.scene.Node;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.ListCell;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;

import java.util.*;
import java.util.regex.Pattern;

public class TestLine extends ListCell<String> {
    private final static String OTHER_TYPE_CODE = "OTHER_TYPE";
    private final static String ERROR_MSG_CODE = "ERROR_MSG";
    private final static String USER_INPUT_CODE = "USER_INPUT";
    private final static String USER_CODE = "U@@U";
    private final static String INPUT_PROMPT = ">>>   "; // TODO: empirical adjustment

    private final static String RESERVED_COMMAND_CODE = "RESERVED_COMMAND";
    private final static String DIGITS_CODE = "DIGITS";
    private final static String OTHER_COMMAND_CODE = "OTHER_COMMAND";

    private final static String SEPARATOR = " ";
    private final static String LOCAL_RESOURCE_MATCH_DICT = String.format("%s.regex_type", TestLine.class.getPackageName());
    private final static String LOCAL_RESOURCE_RESERVE_WORD_DICT = String.format("%s.reserved_words", TestLine.class.getPackageName());
    private final static String regexDigits = "\\-?[0-9]+(?:\\.[0-9]+)?";

    private List<Map.Entry<String, Pattern>> matchDictionary;

    {setContentDisplay(ContentDisplay.GRAPHIC_ONLY); setStyle("-fx-font-family: \"Consolas\";");}

    @Override
    protected void updateItem(String str, boolean empty){
        System.out.println("updating a new item "+str);
        super.updateItem(str, empty);
        if (!checkEmpty(str)){
            System.out.println(str+" is not empty");
            setGraphic(createTextFlow(str));
        }
        else{
            System.out.println(str+" is empty");
            setGraphic(null);
        }
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

        for (String textStr: textsStr){
            //System.out.println(textStr+" "+getTextStrType(textStr));
            ColorText text = new ColorText(textStr, getTextStrType(textStr));
            flow.getChildren().addAll(text, createSpacer());
        }
        return flow;
    }

    private Node createOtherFlow(String textLine){
        String[] textsStr = textLine.split(SEPARATOR);
        TextFlow flow = new TextFlow();

        for (String textStr: textsStr){
            ColorText text = new ColorText(textStr, OTHER_TYPE_CODE);
            flow.getChildren().addAll(text, createSpacer());
        }
        return flow;
    }

    private String stripInputText(String input){
        System.out.println(input);
        return input.substring(INPUT_PROMPT.length()+USER_CODE.length(), input.length());
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

    private boolean isReservedWord(String str){
        List<Map.Entry<String, Pattern>> reserveWordDictionary = initializeDictionary(LOCAL_RESOURCE_RESERVE_WORD_DICT);
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
