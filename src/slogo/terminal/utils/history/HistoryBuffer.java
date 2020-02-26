package slogo.terminal.utils.history;

/**
 * HistoryBuffer enables the terminal to have an iterateable buffer that stores the previous commands.
 * To-do list: maybe it should extend List<String>?
 */
public class HistoryBuffer{
    private static int BUFFER_LIMIT = 20;
    private static String NULL_BUFFER_ENTRY = "";
    private final String USER_INPUT_CODE = "U@@U";

    private String[] buffer;
    private int currentSize;
    private int index;
    private int storage_index;

    /**
     * Constructor
     */
    public HistoryBuffer(){
        initializeBuffer();
    }

    /**
     * Initializes the buffer
     */
    public void initializeBuffer(){
        buffer = new String[BUFFER_LIMIT];
        currentSize = 0;
        storage_index = -1;
        index = 0;
    }

    /**
     * Returns the command string prior to the current position of the pointer.
     * also moves the pointer to its previous position (moves to the last if out of bounds)
     * @return command string
     */
    public String getPrevEntry(){
        if (isEmpty()){
           return handleEmptyBuffer();
        }
        return buffer[moveIndexBy(-1)];
    }

    /**
     * Returns the command string next to the current position of the pointer
     * also moves the pointer to its next position (moves to the first if out of bounds)
     * @return command string
     */
    public String getNextEntry(){
        if (isEmpty()){
            return handleEmptyBuffer();
        }
        return buffer[moveIndexBy(1)];
    }

    /**
     * Adds a new command string to the buffer
     * (If exceeds the limit of the buffer memory, the oldest entry will be erased)
     * @param entry new string command
     * @param num currently default = 1
     */
    public void addEntry(String entry, int num){
        updateCurrentSize(num);
        buffer[updateStorageIndex(num)] = stripInputText(entry);
    }

    /**
     * returns true if the buffer is empty
     * @return boolean status
     */
    public boolean isEmpty(){
        return currentSize == 0;
    }

    /**
     * resets the index to point to the bottom of the buffer
     * added by Maverick
     */
    public void resetIndex() {
        index = storage_index + 1;
    }

    private String handleEmptyBuffer(){
        //System.out.println("Out of Bounds in Buffer");
        return NULL_BUFFER_ENTRY;
    }

    private int moveIndexBy(int inc){
        //index = (index+inc)%currentSize;
        index += inc;
        if (index >= currentSize) index-= currentSize;
        if (index < 0) index += currentSize;
        System.out.println(index);
        return index;
    }

    private void updateCurrentSize(int inc){
        currentSize+=inc;
        if (currentSize > BUFFER_LIMIT) currentSize = BUFFER_LIMIT;
    }

    private int updateStorageIndex(int inc){
        storage_index = (storage_index+inc)%currentSize;
        return storage_index;
    }

    private String stripInputText(String input){
        return input.substring(USER_INPUT_CODE.length());
    }

}
