package cc.mcwarzone.message;

import java.util.HashMap;

public class MessageUtils {
    public static final HashMap<String,String> MESSAGEMAP = new HashMap<>();
    public static void putMessageMap (String path,String message){
        MESSAGEMAP.put(path,message);
    }
    public static String getMessage(String path){
        return MESSAGEMAP.get(path);
    }
}
