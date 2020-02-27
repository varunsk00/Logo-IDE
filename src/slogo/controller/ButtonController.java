package slogo.controller;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;
public class ButtonController {

    private ResourceBundle myResources;

    private List<String> languages;
    private static final String SUPPORTED_LANGUAGES = "src/slogo/resources/languages/LanguageList.txt";

    private List<String> helpPrompts;
    private static final String PROMPTS = "src/slogo/resources/languages/HelpPrompts.txt";

    private boolean loadFilePressed;
    private boolean turtleImagePressed;
    private boolean penColorPressed;
    private boolean backgroundColorPressed;
    private String languagePressed;
    private String helpPressed;

    private HBox myButtons;

    //TODO(FUN): CREATE VARIABLE PEN WIDTH SLIDER
    //TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON
    //TODO(FUN): RESIZE TURTLE SLIDER

    public ButtonController(String language) throws FileNotFoundException {
        myResources = ResourceBundle.getBundle(language);
        this.languages = text2Regex(new File(SUPPORTED_LANGUAGES));
        this.helpPrompts = text2Regex(new File(PROMPTS));
        this.loadFilePressed = false;
        this.turtleImagePressed = false;
        this.penColorPressed = false;
        this.backgroundColorPressed = false;
        this.helpPressed = myResources.getString("HelpButton");
        this.languagePressed = myResources.getString("LanguageButton");
        renderButtons();
    }

    public HBox getHBox() {
        return myButtons;
    }

    public boolean getImageStatus() {
        return turtleImagePressed;
    }

    public boolean getFileStatus() {
        return loadFilePressed;
    }

    public boolean getPenColorStatus() {
        return penColorPressed;
    }

    public boolean getBackgroundColorStatus() {
        return backgroundColorPressed;
    }

    public String getHelpStatus() {
        return helpPressed;
    }

    public String getLanguageStatus() {
        return languagePressed;
    }

    public void setImageOff() {
        turtleImagePressed = false;
    }

    public void setLoadFilePressedOff() {
        loadFilePressed = false;
    }

    public void setPenColorOff() {
        penColorPressed = false;
    }

    public void setBackgroundColorOff() {
        backgroundColorPressed = false;
    }

    private void renderButtons() {
        myButtons = new HBox();
        Button loadButton = makeButton("LoadButton", event -> loadFilePressed = true);
        Button imageButton = makeButton("ImageButton", event -> turtleImagePressed = true);
        Button penButton = makeButton("PenButton", event -> penColorPressed = true);
        Button backgroundButton = makeButton("BackgroundButton", event -> backgroundColorPressed = true);
        ComboBox helpMenu = makeDropDown("HelpButton", helpPrompts);
        ComboBox langMenu = makeDropDown("LanguageButton", languages);

        myButtons.getChildren().addAll(loadButton, imageButton, penButton, backgroundButton, helpMenu, langMenu);

        formatButton(loadButton);
        formatButton(imageButton);
        formatButton(penButton);
        formatButton(backgroundButton);
        formatBox(helpMenu);
        formatBox(langMenu);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(myResources.getString(key));
        tempButton.setMaxWidth(Double.MAX_VALUE);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private ComboBox makeDropDown(String key, List<String> options){
        ComboBox tempMenu = new ComboBox();
        tempMenu.getItems().addAll(options);
        tempMenu.setValue(myResources.getString(key));
        if(key.equals("LanguageButton")){
            tempMenu.setOnAction(event -> languagePressed = (String) tempMenu.getValue());
        }
        else if (key.equals("HelpButton")){
            tempMenu.setOnAction(event -> helpPressed = (String) tempMenu.getValue());
        }
        return tempMenu;
    }

    private void formatButton(Button tempButton) {
        myButtons.setHgrow(tempButton, Priority.ALWAYS);
    }

    private void formatBox(ComboBox tempMenu) {
        myButtons.setHgrow(tempMenu, Priority.ALWAYS);
    }

    private List<String> text2Regex(File dataFile) throws FileNotFoundException {
        List<String> ret = new ArrayList<>();
        Scanner scanner = new Scanner(dataFile);
        while(scanner.hasNextLine()){
            ret.add(myResources.getString(scanner.nextLine()));
        }
        return ret;
    }
}
