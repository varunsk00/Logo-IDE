package terminal.utils.UI;

import javafx.scene.control.ListView;
import terminal.utils.textLines.TestLine;

public class OutputPanel extends ListView {

    public OutputPanel(int width, int max_height){
        setCellFactory(listView -> new TestLine());
    }


    public String getSelectedText(){
        return null;
    }
}
