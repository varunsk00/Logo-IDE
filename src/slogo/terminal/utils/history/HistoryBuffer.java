package slogo.terminal.utils.history;

import java.util.ArrayList;
import java.util.List;

/**
 * HistoryBuffer enables the terminal to have an iterateable buffer that stores the previous
 * commands. To-do list: maybe it should extend List<String>?
 */
public class HistoryBuffer {

  private static int BUFFER_LIMIT = 20;
  private final String USER_INPUT_CODE = "U@@U";

  private String[] buffer;
  private int currentSize;
  private int index;
  private int storage_index;
  private boolean initialized;

  private List<String> commands;
  private List<String> messages;
  private int cnt;

  /**
   * Constructor
   */
  public HistoryBuffer() {
    initializeBuffer();
    initializeHistory();
  }

  private void initializeBuffer() {
    buffer = new String[BUFFER_LIMIT];
    initialized = false;
    currentSize = 0;
    storage_index = -1;
    index = 0;
  }

  private void initializeHistory() {
    commands = new ArrayList<>();
    messages = new ArrayList<>();
    cnt = 0;
  }

  public void addHistory(String command, String systemMessage) {
    commands.add(String.format("%d: %s", cnt++, command));
    messages.add(systemMessage);
  }

  public void removeLastHistory() {
    if (!messages.isEmpty()) {
      messages.remove(messages.size() - 1);
    }
    if (!commands.isEmpty()) {
      commands.remove(commands.size() - 1);
    }
  }

  public List<String> getCommands() {
    return commands;
  }

  public List<String> getMessages() {
    return messages;
  }

  /**
   * Returns the command string prior to the current position of the pointer. also moves the pointer
   * to its previous position (moves to the last if out of bounds)
   *
   * @return command string
   */
  public String getPrevBufferEntry() {
    if (!initialized) {
      index = currentSize;
      initialized = true;
    }
    if (isEmpty()) {
      return handleEmptyBuffer();
    }
    return buffer[moveIndexBy(-1)];
  }

  /**
   * Returns the command string next to the current position of the pointer also moves the pointer
   * to its next position (moves to the first if out of bounds)
   *
   * @return command string
   */
  public String getNextBufferEntry() {
    if (!initialized) {
      index = currentSize;
      initialized = true;
    }
    if (isEmpty()) {
      return handleEmptyBuffer();
    }
    return buffer[moveIndexBy(1)];
  }

  /**
   * Adds a new command string to the buffer (If exceeds the limit of the buffer memory, the oldest
   * entry will be erased)
   *
   * @param entry new string command
   * @param num   currently default = 1
   */
  public void addBufferEntry(String entry, int num) {
    updateCurrentSize(num);
    buffer[updateStorageIndex(num)] = stripInputText(entry);
    resetIndex(); // Credit to Maverick
  }

  /**
   * returns true if the buffer is empty
   *
   * @return boolean status
   */
  public boolean isEmpty() {
    return currentSize == 0;
  }

  /**
   * resets the index to point to the bottom of the buffer added by Maverick
   */
  public void resetIndex() {
    index = storage_index + 1;
  }

  private String handleEmptyBuffer() {
    return "";
  }

  private int moveIndexBy(int inc) {
    index += inc;
    if (index >= currentSize) {
      index -= currentSize;
    }
    if (index < 0) {
      index += currentSize;
    }
    return index;
  }

  private void updateCurrentSize(int inc) {
    currentSize += inc;
    if (currentSize > BUFFER_LIMIT) {
      currentSize = BUFFER_LIMIT;
    }
  }

  private int updateStorageIndex(int inc) {
    storage_index = (storage_index + inc) % currentSize;
    return storage_index;
  }

  private String stripInputText(String input) {
    return input.substring(USER_INPUT_CODE.length());
  }

}
