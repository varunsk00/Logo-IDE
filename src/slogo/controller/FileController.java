package slogo.controller;

import javafx.stage.FileChooser;

import java.io.File;
import java.util.ResourceBundle;

public class FileController {
    private ResourceBundle myResources;
    private FileChooser myFileChooser;
    private String[] validExtensions;

    public FileController(String extensions, String directory, String type, String language){
        myResources = ResourceBundle.getBundle(language);
        myFileChooser = makeChooser(extensions, directory, type);
    }

    public FileChooser getFileChooser(){
        return myFileChooser;
    }

    public String[] getValidExtensions(){
        return validExtensions;
    }

    private FileChooser makeChooser(String extensionsAccepted, String directory, String type) {
        validExtensions = extensionsAccepted.split(",");
        FileChooser result = new FileChooser();
        result.setTitle(myResources.getString("OpenFile"));
        result.setInitialDirectory(new File(System.getProperty("user.dir"),directory));
        result.getExtensionFilters()
                .setAll(new FileChooser.ExtensionFilter(type, validExtensions));
        return result;
    }
}
