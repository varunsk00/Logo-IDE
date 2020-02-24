package slogo.terminal.utils.textLines;

import javafx.scene.paint.Color;
import javafx.scene.text.Text;

/**
 * ColorText fills color to the text according to its type
 */
public class ColorText extends Text {

    /**
     * Constructor
     * @param str text content
     * @param type text type
     */
    public ColorText(String str, String type){
        super(str);
        setFill(getColorfromType(type));
    }

    private Color getColorfromType(String type){

        if (type.equals("ERROR_MSG")){
            return Color.rgb(161, 34, 34);
        }
        if (type.equals("SUCCESS_MSG")){
            return Color.rgb(44, 180, 87);
        }
        if (type.equals("BANNER_MSG")){
            return Color.rgb(238, 233, 229);
        }
        if (type.equals("OTHER_MSG")){
            return Color.rgb(238, 233, 229);
        }

        if (type.equals("RESERVED_COMMAND")){
            return Color.rgb(113, 112, 255);
        }
        if (type.equals("DIGITS")){
            return Color.rgb(253, 247, 124);
        }
        if (type.equals("COMMENT")){
            return Color.rgb(105, 105, 105);
        }
        if (type.equals("OTHER_COMMAND")){
            return Color.rgb(238, 233, 229);
        }

        System.out.println("Error: unimplemented string type in terminal display");
        //default color
        return Color.rgb(238, 233, 229);
    }
}
