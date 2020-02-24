package terminal.utils.UI;

import javafx.collections.FXCollections;
import javafx.scene.control.ListView;
import terminal.utils.textLines.TestLine;


public class OutputPanel extends ListView<String> {
    private final static String CSS_FILEPATH = "/output_panel.css";

    public OutputPanel(int width, int max_height){
        super(FXCollections.observableArrayList());

        //disable focus
        //setMouseTransparent(true);
        setFocusTraversable(false);

        setMinWidth(width); setMaxWidth(width);
        setMaxHeight(max_height); // grows with the list

        getStylesheets().add(getClass().getResource(CSS_FILEPATH).toExternalForm());

        setCellFactory(listView -> new TestLine());
    }

    public void addTexts(String str){
        System.out.println("added text "+str);
        getItems().add(str);
    }

    public void clearTexts(){
        getItems().clear();
    }

    public String getSelectedText(){
        return null;
    }
}
