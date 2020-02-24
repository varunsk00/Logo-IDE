package terminal.utils.history;

public class HistoryBuffer{
    private static int BUFFER_LIMIT = 20;
    private static String NULL_BUFFER_ENTRY = "";
    private final String USER_INPUT_CODE = "U@@U";

    private String[] buffer;
    private int currentSize;
    private int index;
    private int storage_index;

    public HistoryBuffer(){
        initializeBuffer();
    }

    public void initializeBuffer(){
        buffer = new String[BUFFER_LIMIT];
        currentSize = 0;
        storage_index = -1;
        index = 0;
    }

    public String getPrevEntry(){
        if (isEmpty()){
           return handleEmptyBuffer();
        }
        return buffer[moveIndexBy(-1)];
    }

    public String getNextEntry(){
        if (isEmpty()){
            return handleEmptyBuffer();
        }
        return buffer[moveIndexBy(1)];
    }

    public void addEntry(String entry, int num){
        updateCurrentSize(num);
        buffer[updateStorageIndex(num)] = stripInputText(entry);
    }

    public boolean isEmpty(){
        return currentSize == 0;
    }

    private String handleEmptyBuffer(){
        System.out.println("Out of Bounds in Buffer");
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
        return input.substring(USER_INPUT_CODE.length(), input.length());
    }

}
