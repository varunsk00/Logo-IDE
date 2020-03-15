package slogo.variable_panels.util_classes;

import javafx.beans.property.SimpleStringProperty;

/**
 * TableEntry serves as the ValueCellFactory for the AutoTableView class. It takes a pair of String
 * values and manages its update.
 *
 * @author Qiaoyi Fang
 */
public class TableEntry {

  private final SimpleStringProperty key;
  private final SimpleStringProperty value;

  /**
   * Constructor
   *
   * @param key   key text string
   * @param value value text string
   */
  public TableEntry(String key, String value) {
    this.key = new SimpleStringProperty(key);
    this.value = new SimpleStringProperty(value);
  }

  /**
   * Gets the string text of key
   *
   * @return text string
   */
  public String getKey() {
    return key.get();
  }

  /**
   * Sets the string text to key
   *
   * @param str new text string
   */
  public void setKey(String str) {
    key.set(str);
  }

  /**
   * Gets the string text of value
   *
   * @return text string
   */
  public String getValue() {
    return value.get();
  }

  /**
   * Sets the string text to value
   *
   * @param str new text string
   */
  public void setValue(String str) {
    value.set(str);
  }
}
