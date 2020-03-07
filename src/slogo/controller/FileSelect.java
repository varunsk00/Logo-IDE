package slogo.controller;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;

public class FileSelect {
    private ResourceBundle myResources;
    private javafx.stage.FileChooser myFileChooser;
    private String[] validExtensions;

    public FileSelect(String extensions, String directory, String type, String language){
        myResources = ResourceBundle.getBundle(language);
        myFileChooser = makeChooser(extensions, directory, type);
    }

    public javafx.stage.FileChooser getFileChooser(){
        return myFileChooser;
    }

    public String[] getValidExtensions(){
        return validExtensions;
    }

    private javafx.stage.FileChooser makeChooser(String extensionsAccepted, String directory, String type) {
        validExtensions = extensionsAccepted.split(",");
        javafx.stage.FileChooser result = new javafx.stage.FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        result.setInitialDirectory(new File(System.getProperty("user.dir"),directory));
        result.getExtensionFilters()
                .setAll(new javafx.stage.FileChooser.ExtensionFilter(type, validExtensions));
        return result;
    }
}
