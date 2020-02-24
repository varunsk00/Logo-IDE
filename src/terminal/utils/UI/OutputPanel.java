package terminal.utils.UI;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.ListView;
import terminal.utils.textLines.TestLine;

//TODO USER INPUT NEED TO HAVE >>> BEFORE IT
public class OutputPanel extends ListView<String> {

    public OutputPanel(int width, int max_height){
        super(FXCollections.observableArrayList("fuvk", "gfgf", "fg"));

        //disable focus
        setMouseTransparent(true);
        setFocusTraversable(false);

        setMinWidth(width); setMaxWidth(width);
        setMaxHeight(max_height); // grows with the list

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
