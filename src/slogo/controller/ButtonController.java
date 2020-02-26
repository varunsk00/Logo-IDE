package slogo.controller;
import javafx.event.EventHandler;
import javafx.scene.control.*;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

import java.util.*;
public class ButtonController {

    private ResourceBundle myResources;

    private List<String> languages;

    private boolean loadFilePressed;
    private boolean turtleImagePressed;
    private boolean penColorPressed;
    private boolean backgroundColorPressed;
    private boolean helpPressed;
    private String languagePressed;

    private HBox myButtons;

    //TODO(FUN): CREATE VARIABLE PEN WIDTH SLIDER
    //TODO(FUN): DIFF LINE TYPES (DOTTED, DASHED) BUTTON
    //TODO(FUN): RESIZE TURTLE SLIDER

    /**
     * Basic constructor for a header Sets its ResourceBundle to the one specified by the given
     * language Sets all instance variables to false, indicating that no buttons have been pressed
     * Calls renderHeader() which creates the HBox with Buttons Assumes that language exists in
     * Resources folder, otherwise throws an InvocationTargetException error
     *
     * @param language is the language of the ResourceBundle
     */
    public ButtonController(String language) {
        myResources = ResourceBundle.getBundle(language);
        this.languages = new ArrayList<>(Arrays.asList(myResources.getString("English"),
                                                        myResources.getString("Chinese"),
                                                        myResources.getString("French"),
                                                        myResources.getString("German"),
                                                        myResources.getString("Italian"),
                                                        myResources.getString("Portuguese"),
                                                        myResources.getString("Russian"),
                                                        myResources.getString("Spanish"),
                                                        myResources.getString("Urdu")));
        this.loadFilePressed = false;
        this.turtleImagePressed = false;
        this.penColorPressed = false;
        this.backgroundColorPressed = false;
        this.helpPressed = false;
        this.languagePressed = myResources.getString("LanguageButton");
        renderButtons();
    }

    /**
     * Basic getter method for the header used in Main
     *
     * @return myHeader which is the HBox private instance variable representing the header (with
     * functional buttons)
     */
    public HBox getHBox() {
        return myButtons;
    }

    /**
     * Basic getter method returning whether or not to play the simulation This triggers the
     * updateStatus() method in main if playPressed is true
     *
     * @return the boolean private instance variable playPressed
     */
    public boolean getImageStatus() {
        return turtleImagePressed;
    }

    public boolean getFileStatus() {
        return loadFilePressed;
    }

    /**
     * Basic getter method returning whether or not to load a file This triggers the handleXML() in
     * CAController
     *
     * @return the boolean private instance variable loadPressed
     */
    public boolean getPenColorStatus() {
        return penColorPressed;
    }

    /**
     * Basic getter method returning whether or not to skip ahead in the game This triggers the
     * skipAhead() method in CAController
     *
     * @return the boolean private instance variable skipPressed
     */
    public boolean getBackgroundColorStatus() {
        return backgroundColorPressed;
    }

    public boolean getHelpStatus() {
        return helpPressed;
    }

    public String getLanguageStatus() {
        return languagePressed;
    }

    /**
     * Basic setter method that sets the loadPressed variable to false Called in handleXML() method to
     * stop the load screen from rerendering
     */
    public void setImageOff() {
        turtleImagePressed = false;
    }

    public void setLoadFilePressedOff() {
        loadFilePressed = false;
    }

    /**
     * Basic setter method that sets the skipPressed variable to false Called in skipAhead() method to
     * only skip once
     */
    public void setPenColorOff() {
        penColorPressed = false;
    }

    /**
     * Basic setter method to set the value of clearPressed to false Called after the Grids have
     * already been cleared in CAController
     */
    public void setBackgroundColorOff() {
        backgroundColorPressed = false;
    }

    public void setHelpOff() {
        helpPressed = false;
    }


    private void renderButtons() {
        myButtons = new HBox();
        Button loadButton = makeButton("LoadButton", event -> loadFilePressed = true);
        Button imageButton = makeButton("ImageButton", event -> turtleImagePressed = true);
        Button penButton = makeButton("PenButton", event -> penColorPressed = true);
        Button backgroundButton = makeButton("BackgroundButton", event -> backgroundColorPressed = true);
        Button helpButton = makeButton("HelpButton", event -> helpPressed = true);
        ComboBox langMenu = makeDropDown("LanguageButton");

        myButtons.getChildren().addAll(loadButton, imageButton, penButton, backgroundButton, helpButton, langMenu);

        formatButton(loadButton);
        formatButton(imageButton);
        formatButton(penButton);
        formatButton(backgroundButton);
        formatButton(helpButton);
        formatBox(langMenu);
    }

    private Button makeButton(String key, EventHandler e) {
        Button tempButton = new Button(myResources.getString(key));
        tempButton.setMaxWidth(Double.MAX_VALUE);
        tempButton.setOnAction(e);
        return tempButton;
    }

    private ComboBox makeDropDown(String key){
        ComboBox tempMenu = new ComboBox();
        tempMenu.getItems().addAll(languages);
        tempMenu.setValue(myResources.getString(key));
        tempMenu.setOnAction(event -> languagePressed = (String) tempMenu.getValue());
        return tempMenu;
    }

    private void formatButton(Button tempButton) {
        myButtons.setHgrow(tempButton, Priority.ALWAYS);
    }

    private void formatBox(ComboBox tempMenu) {
        myButtons.setHgrow(tempMenu, Priority.ALWAYS);
    }
}
