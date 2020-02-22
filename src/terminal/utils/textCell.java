package terminal.utils;

import javafx.scene.Node;
import javafx.scene.control.ListCell;
import javafx.scene.text.TextFlow;

/**
 *
 */
public class textCell extends ListCell<String> {
    private String EMPTY_TEXT = "";
    private String SEPARATOR = " ";

    final static String ERRORMSG_CODE = "+898+";
    final static String SUCCESSMSG_CODE = "+200+";
    final static String BANNER_CODE = "+788+";
    final static String MSG_CODE = "+400+";
    final static String COMMANDLINE_CODE = "+101+";

    @Override
    protected void updateItem(String textLine, boolean empty){
        super.updateItem(textLine, empty);
        if (!checkEmpty(textLine)){
            setGraphic(createTextFlow(textLine));
        }
        else{
            setGraphic(null);
        }
    }

    private boolean checkEmpty(String str){
        return str == null || str.equals(EMPTY_TEXT) || isEmpty();
    }

    /*
    TODO:
        1. feedback:
            - error msg
            - success msg
            - others
                - default banner
                - ...
        -
        2. user input:
            - command line
                - reserve word
                - digit(s)
            - others
     */

    private Node createTextFlow(String textLine){
        TextFlow textFlow = new TextFlow();

        String[] texts = textLine.split(SEPARATOR);

        for(String text:texts){

        }

        return textFlow;
    }

    private void getReource(){}

    private String getType(String str){return null;}

    private boolean isNumber(String str){return false;}

    private boolean isReserved(String str){return false;}

}
