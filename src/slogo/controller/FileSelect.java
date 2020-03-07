package slogo.controller;

import java.io.File;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;

public class FileSelect {

  private ResourceBundle myResources;
  private FileChooser myFileChooser;
  private String[] validExtensions;

  public FileSelect(String extensions, String directory, String type, String language) {
    myResources = ResourceBundle.getBundle(language);
    myFileChooser = makeChooser(extensions, directory, type);
  }

  public FileChooser getFileChooser() {
    return myFileChooser;
  }

  public String[] getValidExtensions() {
    return validExtensions;
  }

  private FileChooser makeChooser(String extensionsAccepted, String directory, String type) {
    validExtensions = extensionsAccepted.split(",");
    FileChooser result = new FileChooser();
    result.setTitle(myResources.getString("OpenFile"));
    result.setInitialDirectory(new File(System.getProperty("user.dir"), directory));
    result.getExtensionFilters()
        .setAll(new FileChooser.ExtensionFilter(type, validExtensions));
    return result;
  }
}
