package slogo.controller;

import java.io.File;
import java.util.ResourceBundle;
import javafx.stage.FileChooser;

/**
 * FileSelect.java
 * Pop-Up Window to select a File based on valid extensions
 *
 * @author Varun Kosgi
 */
public class FileSelect {

  private ResourceBundle myResources;
  private FileChooser myFileChooser;
  private String[] validExtensions;

  /**
   * Initializes ResourceBundle based on GUI language and FileChooser based on valid extensions
   * @param extensions valid extensions for this instance of a FileChooser
   * @param directory starting directory to begin search
   * @param type file type as String
   * @param language GUI language
   */
  public FileSelect(String extensions, String directory, String type, String language) {
    myResources = ResourceBundle.getBundle(language);
    myFileChooser = makeChooser(extensions, directory, type);
  }

  /**
   * @return FileChooser object
   */
  public FileChooser getFileChooser() {
    return myFileChooser;
  }

  /**
   * @return all valid extensions as a String Array
   */
  public String[] getValidExtensions() {
    return validExtensions;
  }

  private FileChooser makeChooser(String extensionsAccepted, String directory, String type) {
    validExtensions = extensionsAccepted.split(",");
    FileChooser result = new FileChooser();
    result.setTitle(myResources.getString("OpenFile"));
    result.setInitialDirectory(new File(System.getProperty("user.dir"), directory));
    result.getExtensionFilters().setAll(new FileChooser.ExtensionFilter(type, validExtensions));
    return result;
  }
}
